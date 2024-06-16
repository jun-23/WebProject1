package com.project1.oauth.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.domain.kakao.AmountVO;
import com.project1.oauth.domain.kakao.CanceledResponse;
import com.project1.oauth.domain.kakao.KakaoApprovalSave;
import com.project1.oauth.domain.kakao.KakaoPayApprovalVO;
import com.project1.oauth.domain.kakao.KakaoPayReadyVO;
import com.project1.oauth.domain.kakao.KakaoPayRequest;
import com.project1.oauth.domain.kakao.PayInfo;
import com.project1.oauth.repository.KakaoPayApprovalRepository;
import com.project1.oauth.repository.UserRepository;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoPayService {

	private static final String HOST = "https://kapi.kakao.com";

	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;

	@Autowired
	private KakaoPayApprovalRepository kakaoPayApprovalRepository;

	@Autowired
	private UserRepository userRepository;
	private String cid = "TC0ONETIME";
	private Integer partner_order_id = 1;
	private KakaoPayApprovalVO data;
	private Integer total = 0;
	private String yourkey = "your";
	private Integer tier = 0;
	
	public String kakaoPayReady(KakaoPayRequest req) {
		data = new KakaoPayApprovalVO();
		total = 0;
		tier = 0;
		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + yourkey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE.toString());
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	
		params.add("partner_user_id", req.getPartner_user_id());
		params.add("item_name", req.getItem_name());
		params.add("quantity", req.getQuantity().toString());
		params.add("total_amount", req.getTotal_amount().toString());
		
		data.setPartner_user_id(req.getPartner_user_id());
		data.setPartner_order_id(partner_order_id.toString());
		total = req.getTotal_amount();
		tier = req.getTier(); //티어를 갱신
		
		//고정 기능
		params.add("partner_order_id", partner_order_id.toString());
		partner_order_id++;
		params.add("tax_free_amount", "0");
		params.add("cid", cid);
		params.add("approval_url", "http://localhost:8080/api/kakaopay/Success");
		params.add("cancel_url", "http://localhost:8080/api/kakaopay/kakaoPayCancel");
		params.add("fail_url", "http://localhost:8080/api/kakaopay/kakaoPaySuccessFail");

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
					KakaoPayReadyVO.class);
			data.setTid(kakaoPayReadyVO.getTid());
			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/main";

	}

	public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

		System.out.println("KakaoPayInfoVO............................................");
		System.out.println("-----------------------------");

		RestTemplate restTemplate = new RestTemplate();
		System.out.println("1차 통과");
		System.out.println(kakaoPayApprovalVO);
		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + yourkey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE.toString());
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", cid);
		params.add("tid", data.getTid());
		params.add("partner_order_id", data.getPartner_order_id());
		params.add("partner_user_id", data.getPartner_user_id());
		params.add("pg_token", pg_token);
		params.add("total_amount", total.toString());

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body,
					KakaoPayApprovalVO.class);
//			log.info("" + kakaoPayApprovalVO);
			System.out.println(kakaoPayApprovalVO);
			return kakaoPayApprovalVO;

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public KakaoApprovalSave saveApproval(KakaoApprovalSave approval, String userEmail) {
		UserDomain user = userRepository.findById(userEmail)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user email"));
		if (user != null) {
			if(tier > 0) {
				user.setSecurityGrade(tier);
			}
			approval.setUser(user);
			tier = 0;//초기화
		}
		return kakaoPayApprovalRepository.save(approval);
	}

	public List<KakaoApprovalSave> getList(String email) {

		return kakaoPayApprovalRepository.findByUserEmail(email);
	}

	public PayInfo getDetail(long id) { // 조회
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + yourkey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE.toString());
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		KakaoApprovalSave info = kakaoPayApprovalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id"));
		params.add("cid", info.getCid());
		params.add("tid", info.getTid());
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		try {
			PayInfo payinfo = restTemplate.postForObject(new URI(HOST + "/v1/payment/order"), body, PayInfo.class);
			System.out.println(payinfo);
			System.out.println("취소 2단계 통과");
			return payinfo;
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	public CanceledResponse cancelOrder(PayInfo info, long id) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + yourkey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE.toString());
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", info.getCid());
		params.add("tid", info.getTid());
		params.add("cancel_amount", info.getAmount().getTotal().toString());
		params.add("cancel_tax_free_amount", info.getAmount().getTax_free().toString());
		
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		KakaoApprovalSave approval_info = kakaoPayApprovalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id"));
		
		try {
			CanceledResponse res = restTemplate.postForObject(new URI(HOST + "/v1/payment/cancel"), body, CanceledResponse.class);
			System.out.println(res);
			System.out.println("취소 3단계 통과");
			approval_info.setFlag(0);
			kakaoPayApprovalRepository.save(approval_info); // 취소되었단 뜻으로 false처리 
			
			return res;
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}

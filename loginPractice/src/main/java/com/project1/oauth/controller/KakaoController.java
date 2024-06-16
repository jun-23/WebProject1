package com.project1.oauth.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project1.oauth.domain.kakao.CanceledResponse;
import com.project1.oauth.domain.kakao.KakaoApprovalSave;
import com.project1.oauth.domain.kakao.KakaoPayApprovalVO;
import com.project1.oauth.domain.kakao.KakaoPayRequest;
import com.project1.oauth.domain.kakao.PayInfo;
import com.project1.oauth.service.KakaoPayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "KakaoPay", description = "결제 관련 API")
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/kakaopay")
public class KakaoController {

	private final KakaoPayService kakaopay;
	private Integer tier = 0;

	public KakaoController(KakaoPayService kakaopay) {
		this.kakaopay = kakaopay;
	}

	@GetMapping("/test")
	public ModelAndView test() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("kakaoPay");
		return modelAndView;
	}

	@Operation(summary = "상품 결제")
	@PostMapping("/buy")
	public ModelAndView kakaoPay(KakaoPayRequest req) {
		tier = 0; // 초기화 
	    System.out.println(req);
	    if(req.getTier() > 0) {
	    	tier = req.getTier(); //티어 상품을 구매할 경우에만 갱신 
	    }
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:" + kakaopay.kakaoPayReady(req));
	    System.out.println("상품 결제 통과");
	    return modelAndView;
	}

	@Operation(summary = "상품 결제 완료")
	@RequestMapping("/Success") // 주소를 옮기는 방법과 kakaopay Service 쪽의 승인, 취소, 실패 시 주소가 잘못되어 있었음
	public RedirectView kakaoPaySuccess(@RequestParam("pg_token") String pg_token) {
		KakaoPayApprovalVO vo = kakaopay.kakaoPayInfo(pg_token); // 승인 확인 보내면
		System.out.println(vo);
		KakaoApprovalSave approval = new KakaoApprovalSave();
		approval.setAid(vo.getAid());
		approval.setCid(vo.getCid());
		approval.setTid(vo.getTid());
		approval.setItem(vo.getItem_name());
		if(tier > 0) {
			approval.setFlag(2);
		}else {
			approval.setFlag(1);
		}
		kakaopay.saveApproval(approval, vo.getPartner_user_id());
		RedirectView redirect = new RedirectView();
		redirect.setUrl("http://localhost:5173");
		return redirect;
	}

	// 구매 정보 가져오기
	@Operation(summary = "결제 리스트")
	@GetMapping("/list/{email}")
	public List<KakaoApprovalSave> list(@PathVariable("email") String email) {
		List<KakaoApprovalSave> list = kakaopay.getList(email);
		return list;
	}

	@Operation(summary = "결제 상세")
	@GetMapping("/detail/{id}")
	public PayInfo detail(@PathVariable("id") long id) {
		System.out.println(id);
		PayInfo info = kakaopay.getDetail(id);
		System.out.println(info);
		info.setCost(info.getAmount().getTotal());
		return info;

	}

	@Operation(summary = "결제 취소")
	@GetMapping("/cancel/{id}")
	public RedirectView cancel(@PathVariable("id") long id) {
		System.out.println("취소 1단계 통과");
		PayInfo info = kakaopay.getDetail(id); // 주문조회
		kakaopay.cancelOrder(info, id);
		RedirectView redirect = new RedirectView();
		redirect.setUrl("http://localhost:5173");
		return redirect;

	}

	@Operation(summary = "상품 결제 요청")
	@GetMapping("/buy")
    public ModelAndView kakaoPayBuy(@RequestParam("partner_user_id") String partnerUserId,
                                     @RequestParam("item_name") String itemName,
                                     @RequestParam("quantity") int quantity,
                                     @RequestParam("total_amount") int totalAmount,
                                     @RequestParam("tier") int tier,
                                     Model model) {
        // 결제 요청 처리를 위한 로직을 추가합니다.
//        KakaoPayRequest req = new KakaoPayRequest();
//        req.setItem_name(itemName);
//        req.setPartner_user_id(partnerUserId);
//        req.setQuantity(quantity);
//        req.setTotal_amount(totalAmount);
//        System.out.println(req);

        // 모델에 값 설정
        model.addAttribute("partner_user_id", partnerUserId);
        model.addAttribute("item_name", itemName);
        model.addAttribute("quantity", quantity);
        model.addAttribute("total_amount", totalAmount);
        model.addAttribute("tier", tier);

        // test.html로 이동
        return new ModelAndView("test");
    }
}


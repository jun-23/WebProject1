package com.project1.oauth.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service	
public class UserService extends DefaultOAuth2UserService{
	
	@Autowired UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		
		//email, oauthType 호출
		Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
		System.out.println(("ATTR INFO : {}" + attributes.toString()));
		
		String email = null;
		String oauthType = userRequest.getClientRegistration().getRegistrationId();
		
//		System.out.println("왜 안들어와 "+oauthType);
		
		OAuth2User user2 = super.loadUser(userRequest);
		
		//oauth 타입에 따라 데이터가 다르기에 분기
		if("google".equals(oauthType.toLowerCase())) { // Google
			email = attributes.get("email").toString();
			
		}
		else if("naver".equals(oauthType.toLowerCase())) { // Naver
			System.out.println("일루 오냐?!");
			// naver는 response 내에 email이 존재함.
			email = ((Map<String, Object>) attributes.get("response")).get("email").toString();
			
		}
		else if("kakao".equals(oauthType.toLowerCase())) { // Kakao
			// kakao는 kakao_account 내에 email이 존재함.
			email = ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();
			
		}
		
		
		
		// user 존재 여부 확인 및 없으면 생성(이메일과 Oauth 접근 타입을 저장한다)
		if(getUserByEmailAndOAuthType(email, oauthType) == null) {
//			
			UserDomain user = new UserDomain();
			user.setEmail(email);
			user.setOauthType(oauthType);
			user.setSecurityGrade(0);
			save(user);
		}
		
		return super.loadUser(userRequest);
		
	}

	public void save(UserDomain user) {
		userRepository.save(user);
		
	}

	public UserDomain getUserByEmailAndOAuthType(String email, String oauthType) {
		return userRepository.findByEmailAndOauthType(email, oauthType).orElse(null);
	}
	
	//유저 이메일을 받으면 safe_graded를 반환한다. tier의 접근성을 위한 함수
	public Integer getGrade(String email) {
		UserDomain user;
		try {
			user = userRepository.findById(email).orElseThrow(() -> new IllegalAccessException("Invalid user Email"));
			return user.getSecurityGrade();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}

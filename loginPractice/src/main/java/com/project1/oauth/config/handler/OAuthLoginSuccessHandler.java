package com.project1.oauth.config.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
		
		//토큰에서 email. oauthType 추출
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		
		String email = null;
		String oauthType = token.getAuthorizedClientRegistrationId();
		
		System.out.println(oauthType);
		
		//성공시에 OAuth2.0 로그인 타입에 맞추어서 이메일을 가져와 저장함
		
		if("google".equals(oauthType.toLowerCase())) {
			email = token.getPrincipal().getAttribute("email").toString();
		}
		else if("naver".equals(oauthType.toLowerCase())) {
			// naver는 response 내에 email이 존재함.
			email = ((Map<String, Object>) token.getPrincipal().getAttribute("response")).get("email").toString();
		}else if("kakao".equals(oauthType.toLowerCase())) {
			// kakao는 kakao_account 내에 email이 존재함.
			email = ((Map<String, Object>) token.getPrincipal().getAttribute("kakao_account")).get("email").toString();
		}
		
		UserDomain user = userService.getUserByEmailAndOAuthType(email, oauthType);
		
		//세선에 user 저장
		HttpSession session = request.getSession();
		session.setAttribute("user", user); //이미 세션에 유저 Email을 담고 있다.
		//UserRestController에서 세션의 유저토큰과 유저Email을 가져오는 메서드를 통해서 유저 정보를 불러올 수 있다.
		//즉 애진작에 Vue에서 처리해도 되고, 아니면, 그때그때 REST로 가져올 수도 있다.
		
		
//		//세션에 유저 정보가 잘 들어갔는지 확인하기 위한 Systout들
//		System.out.println("User authenticated: {}" + user.getEmail());
//		System.out.println("Session ID: {}" + session.getId());		
//		//아래는 확인용 Sysout들
//		System.out.println("토큰 정보 : " + token.getDetails());
//		System.out.println("session 정보 : " + session.getId());
		
		System.out.println("로그인 성공");
		//로그인 성공 후에 최종적으로 가는 URL 위치를 아래와 같이 변경해준다.
		super.setDefaultTargetUrl("http://localhost:5173/redirect"); 
		//이동 후에 해당 RedirectView에서는 쿠키를 수집하고 우리가 원하는 화면으로 이동시킨다.
		super.onAuthenticationSuccess(request, response, authentication);
		
	}
	
	
}

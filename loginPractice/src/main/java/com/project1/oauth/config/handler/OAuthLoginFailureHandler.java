package com.project1.oauth.config.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.project1.oauth.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuthLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired UserService userService;
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{
		
		System.out.println("실패");
		
		System.out.println("LOGIN FAILED : {}" + exception.getMessage());
		System.out.println("response.getStatus() : {}" + response.toString());
		
		
		super.onAuthenticationFailure(request, response, exception);
		
	}
	
}

package com.project1.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.project1.oauth.config.handler.OAuthLoginFailureHandler;
import com.project1.oauth.config.handler.OAuthLoginSuccessHandler;
import com.project1.oauth.service.UserService;

@Configuration
@EnableWebSecurity // Web 보안 활성화
public class SecurityConfig {

	@Autowired
	OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
	@Autowired
	OAuthLoginFailureHandler oAuthLoginFailureHandler;
	@Autowired
	UserService userService;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	//Oauth2.0을 통해 로그인을 해주는 역할
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		.authorizeHttpRequests(auth -> { //url 접근 권한을 정의
			auth.requestMatchers("/login", "/userapi/login", "/videoapi/**", "/api/shop/**", "/api/kakaopay/**","/api/posts/**").permitAll() //해당 url로 들어오는 요청은 모두 승인
			.anyRequest().authenticated(); //다른 모든 요청은 인증된 사용자만 접근 가능!
			//즉 위 두개를 통해서, 무료로 공개된 api와 아닌 api를 설정할 수 있음
		})
		.csrf(c -> c.disable()) // csrf 보호를 비활성화
		.oauth2Login(oauth2 -> { // Oauth2 로그인 설정을 정의
			oauth2.successHandler(oAuthLoginSuccessHandler) // 성공시 해당 핸들러 작동
			.failureHandler(oAuthLoginFailureHandler) // 실패시 해당 핸들러 작동
			.tokenEndpoint(tokenEndpoint->
			tokenEndpoint.accessTokenResponseClient(authorizationCodeTokenResponseClient()));
		}).formLogin(Customizer.withDefaults()); // 기본 폼 로그인을 만들어서 보여줌 - 우린 안쓰는데 나중에 지워도 됨

		return http.build(); //위 설정을 기반으로 SecurityFilter Chain 객체를 반환

	}

	@Bean
    public DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRequestEntityConverter(new OAuth2AuthorizationCodeGrantRequestEntityConverter());
        return tokenResponseClient;
    }
	
}

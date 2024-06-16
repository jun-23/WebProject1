package com.project1.oauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userapi")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") //vue와 통신을 위한 CrossOrigin
public class UserRestController {
	
	private final UserService userService;
	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginform(HttpSession session) {

		System.out.println("세션 정보를 파악해서 vue로 전달");
		Map<String, String> userTokenAndEmail = new HashMap<>(); //정보 전달용 Map
		
		if (session.getAttribute("user") == null) { // session에 "user" 정보가 비었을 경우

			return new ResponseEntity<>("유저정보 없음", HttpStatus.OK); // 유저 정보 없음 반환
			
		} else { //session에 유저정보가 잘 있다.
			
			UserDomain user = (UserDomain) session.getAttribute("user"); //user Email 추출

			//확인용 Sysout
//			System.out.println("세션 유저 정보 " + user.getEmail());
//			System.out.println("세션 쿠키 : " + session.getId());

			//email과 token 정보를 담아서 return한다.
			userTokenAndEmail.put("email", user.getEmail());
			userTokenAndEmail.put("token", session.getId());

			return new ResponseEntity<Map<String, String>>(userTokenAndEmail, HttpStatus.OK);
		}

	}
	
	//로그아웃 메서드
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session){
		System.out.println("로그아웃 요청 처리 : 세션 초기화");
		
		if(session != null) { // 세션이 있고 없고에 따른 예외처리
			session.invalidate();; // 세션 무효화
			return new ResponseEntity<>("로그아웃되었습니다.", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("세션이 없습니다. -> 오류 페이지로 전송", HttpStatus.BAD_REQUEST);
		}
	}
	//grade 반환
	@GetMapping("/{email}")
	public Integer getUserTier(@PathVariable String email) {
		Integer grade = userService.getGrade(email);
		return grade;
	}
	

}

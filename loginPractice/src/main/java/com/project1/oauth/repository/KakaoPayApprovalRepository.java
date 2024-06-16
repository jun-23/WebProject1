package com.project1.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.oauth.domain.kakao.KakaoApprovalSave;

public interface KakaoPayApprovalRepository extends JpaRepository<KakaoApprovalSave, Long> {
	
	//이메일로 구매 정보를 가져오기 
	 List<KakaoApprovalSave> findByUserEmail(String email);
}
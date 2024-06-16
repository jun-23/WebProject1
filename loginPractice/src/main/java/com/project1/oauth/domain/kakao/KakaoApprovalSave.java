package com.project1.oauth.domain.kakao;

import com.project1.oauth.domain.UserDomain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "kakao_pay_approval")
public class KakaoApprovalSave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String aid;
	
	private String tid;
	
	private String cid;
	
	private String item;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "email")
	private UserDomain user;
	
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public UserDomain getUser() {
		return user;
	}

	public void setUser(UserDomain user) {
		this.user = user;
	}




}
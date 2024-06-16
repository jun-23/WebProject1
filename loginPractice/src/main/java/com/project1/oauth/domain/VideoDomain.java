package com.project1.oauth.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor //파라미터 없는 생성자 자동 생성
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder	
public class VideoDomain {
	@Id
	//length는 길이, columnDefinition은 text등 특정 타입 지정
	@Column(name = "video_id", length = 50, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int videoId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "url", length = 50, nullable = false)			
	private String url;
	
	@Column(name = "maker", length = 20, nullable = false)
	private String maker;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String text;
	
	@Column(name = "part", length = 10, nullable = false)
	private String part;
	
	@Column(name = "runningtime", nullable = false)
	private int runningtime;
	
	@CreationTimestamp
	@Column(name = "regDate")
	private LocalDateTime regDate;
	
	@Column(name = "view_cnt")
	@ColumnDefault("0")
	private int viewCnt;
	
	
	@Enumerated(EnumType.ORDINAL)
	private TierStatus tier;


	public int getVideoId() {
		return videoId;
	}


	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMaker() {
		return maker;
	}


	public void setMaker(String maker) {
		this.maker = maker;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getPart() {
		return part;
	}


	public void setPart(String part) {
		this.part = part;
	}


	public int getRunningtime() {
		return runningtime;
	}


	public void setRunningtime(int runningtime) {
		this.runningtime = runningtime;
	}


	public LocalDateTime getRegDate() {
		return regDate;
	}


	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}


	public int getViewCnt() {
		return viewCnt;
	}


	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}


	public TierStatus getTier() {
		return tier;
	}


	public void setTier(TierStatus tier) {
		this.tier = tier;
	}
	
	
	
}

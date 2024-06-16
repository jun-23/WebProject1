package com.project1.oauth.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentDomain {
	@Id
	@Column(name = "comments_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comments_id;
	
	@ManyToOne //리뷰가 many user가 one 유저는 여러개의 리뷰를 적을 수 있다.
	@JoinColumn(name = "email") //조인하는 컬럼명 //유저의 테이브에서 email를 결합함
	private UserDomain user;
	
	public int getComments_id() {
		return comments_id;
	}

	public void setComments_id(int comments_id) {
		this.comments_id = comments_id;
	}

	public UserDomain getUser() {
		return user;
	}

	public void setUser(UserDomain user) {
		this.user = user;
	}

	public PostDomain getPost() {
		return post;
	}

	public void setPost(PostDomain post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	@ManyToOne //리뷰가 many user가 one 유저는 여러개의 리뷰를 적을 수 있다.
	@JoinColumn(name = "post_id") //조인하는 컬럼명 //유저의 테이브에서 email를 결합함
	private PostDomain post;
	
	@Column(name = "content", length = 300)
	private String content;
	
	@CreationTimestamp
	@Column(name = "regDate")
	private LocalDateTime regDate;
}

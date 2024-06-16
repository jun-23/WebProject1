package com.project1.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.oauth.domain.CommentDomain;

public interface CommentRepository extends JpaRepository<CommentDomain, Integer> {
	List<CommentDomain> findByPost_PostId(int postId);
}

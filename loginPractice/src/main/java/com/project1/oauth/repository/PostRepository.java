package com.project1.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.oauth.domain.PostDomain;

public interface PostRepository extends JpaRepository<PostDomain, Integer> {
}
package com.project1.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.oauth.domain.ProductDomain;

@Repository
public interface ProductRepository extends JpaRepository<ProductDomain, Integer> {
	
}


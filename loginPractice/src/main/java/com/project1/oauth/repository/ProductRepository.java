package com.project1.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.oauth.domain.ProductDomain;

@Repository
public interface ProductRepository extends JpaRepository<ProductDomain, Integer> {
    List<ProductDomain> findByCategory2(String category2);

}


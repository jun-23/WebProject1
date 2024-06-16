package com.project1.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.oauth.domain.TierReviewDomain;
import com.project1.oauth.domain.TierStatus;

@Repository
public interface TierReviewRepository extends JpaRepository<TierReviewDomain, Integer> {
	
//	Optional<TierReviewDomain> findByTierAndPart(TierStatus Tier, String part);
	
}

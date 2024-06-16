package com.project1.oauth.service;

import com.project1.oauth.domain.CommentDomain;
import com.project1.oauth.domain.PostDomain;
import com.project1.oauth.domain.TierReviewDomain;
import com.project1.oauth.domain.TierStatus;
import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.repository.CommentRepository;
import com.project1.oauth.repository.PostRepository;
import com.project1.oauth.repository.TierReviewRepository;
import com.project1.oauth.repository.UserRepository;
import com.project1.oauth.repository.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TierReviewService {

    @Autowired
    private TierReviewRepository tierReviewRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VideoRepository videoRepository;


    public TierReviewDomain createReview(String email, TierStatus tier, TierReviewDomain tierReview) {
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        tierReview.setUser(user);
        tierReview.setTier(tier);
        return tierReviewRepository.save(tierReview);
    }

    public TierReviewDomain updateReview(int reviewId, String email, TierReviewDomain reviewDetails) {
    	TierReviewDomain review = tierReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰는 없어요"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("얘가 쓴 리뷰가 아닌데요?"));

        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException("이 유저는 권한이 없어유");
        }
        review.setTierReview(reviewDetails.getTierReview());
        return tierReviewRepository.save(review);
    }

    public void deleteReview(int reviewId, String email) {
    	TierReviewDomain review = tierReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("이런 리뷰는 없어서 삭제 몬해유"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("이 유저가 없는디?"));

        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException("권한이 없는 유저유");
        }

        tierReviewRepository.delete(review); // 지워봐유
    }
}
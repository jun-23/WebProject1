package com.project1.oauth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.oauth.domain.TierReviewDomain;
import com.project1.oauth.domain.TierStatus;
import com.project1.oauth.domain.VideoDomain;
import com.project1.oauth.repository.TierReviewRepository;
import com.project1.oauth.repository.VideoRepository;
import com.project1.oauth.service.TierReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/videoapi")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class VideoRestController {
	
	//영상과 영상에 대한 리뷰도 한꺼번에 처리하기
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private TierReviewRepository tierReviewRepository;
	
	@Autowired
    private TierReviewService tierReviewService;

	@Operation(summary = "비디오 전체 조회")
	@GetMapping("/video")
	public ResponseEntity<?> select(){
		System.out.println("rest-api video 테스트");
		
		List<VideoDomain> videoList = videoRepository.findAll();

		
//		return new ResponseEntity(header, HttpStatus.OK);
		 return new ResponseEntity<List<VideoDomain>>(videoList, HttpStatus.OK);
	}
	
	// New method to get a video by its ID
	@Operation(summary = "비디오 개별 조회")
    @GetMapping("/video/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Integer id) {
        System.out.println("Fetching video with ID: " + id);

        Optional<VideoDomain> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return new ResponseEntity<VideoDomain>(video.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Video not found", HttpStatus.NOT_FOUND);
        }
    }
	
	//JPA를 사용해서 GETTER와 SETTER을 설정해주었지만, 실제로 HTTP에 값을 보낼 때는 해당 JPA가 작동하지 않아서 빈 값을 보내게 된다.
	// 고로 REST API에서 갯수는 일치하지만 빈 값들을 보내는 현상이 있을 경우 각 도메인에 GETTER SETTER을 설정한다.
	
	@Operation(summary = "클래스당 리뷰 조회")
	@GetMapping("/review")
	public ResponseEntity<?> selectAllReview(){
		System.out.println("영상 클래스에 대한 리뷰를 가져옵니다.");
		
		List<TierReviewDomain> TierReviewList = tierReviewRepository.findAll();
		
//		for(int i = 0; i < TierReviewList.size(); i++) {
//			System.out.println(TierReviewList.get(i).toString());
//		}
				
		return new ResponseEntity<List<TierReviewDomain>>(TierReviewList,HttpStatus.OK);
	}
	
	@Operation(summary = "리뷰 생성")
    @PostMapping("/review")
    public TierReviewDomain createComment(
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email,
            @RequestParam TierStatus tier,
            @RequestBody TierReviewDomain tierReview) {
		System.out.println(tierReview.getTierReview());
        return tierReviewService.createReview(email, tier, tierReview);
    }

    @Operation(summary = "리뷰 수정")
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<?> updateComment(
            @PathVariable int reviewId,
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email,
            @RequestBody TierReviewDomain reviewDetails) {
    	TierReviewDomain updatedReview = tierReviewService.updateReview(reviewId, email, reviewDetails);
        return ResponseEntity.ok(updatedReview);
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable int reviewId,
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email) {
        tierReviewService.deleteReview(reviewId, email);
        return ResponseEntity.noContent().build();
    }
	
	

}

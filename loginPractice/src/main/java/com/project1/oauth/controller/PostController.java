package com.project1.oauth.controller;

import com.project1.oauth.domain.PostDomain;
import com.project1.oauth.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "모든 게시글 조회")
    @GetMapping
    public List<PostDomain> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "특정 게시글 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PostDomain> getPostById(@PathVariable int id) {
        PostDomain post = postService.getPostById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시글 생성")
    @PostMapping
    public PostDomain createPost(
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email, 
            @RequestBody PostDomain post) {
    	System.out.println("게시글 작성 테스트");
    	System.out.println("이메일 ; " + email);
        return postService.createPost(email, post);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<PostDomain> updatePost(
            @PathVariable int id, 
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email, 
            @RequestBody PostDomain postDetails) {
        PostDomain updatedPost = postService.updatePost(id, email, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable int id, 
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email) {
        postService.deletePost(id, email);
        return ResponseEntity.noContent().build();
    }
}
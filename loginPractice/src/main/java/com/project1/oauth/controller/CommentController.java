package com.project1.oauth.controller;

import com.project1.oauth.domain.CommentDomain;
import com.project1.oauth.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "특정 게시물의 모든 댓글 조회")
    @GetMapping
    public List<CommentDomain> getCommentsByPostId(@PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(summary = "특정 댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDomain> getCommentById(
            @PathVariable int postId,
            @PathVariable int commentId) {
        CommentDomain comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "댓글 생성")
    @PostMapping
    public CommentDomain createComment(
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email,
            @PathVariable int postId,
            @RequestBody CommentDomain comment) {
        return commentService.createComment(email, postId, comment);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDomain> updateComment(
            @PathVariable int postId,
            @PathVariable int commentId,
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email,
            @RequestBody CommentDomain commentDetails) {
        CommentDomain updatedComment = commentService.updateComment(commentId, email, commentDetails);
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId,
            @Parameter(description = "유저 이메일", required = true) @RequestParam String email) {
        commentService.deleteComment(commentId, email);
        return ResponseEntity.noContent().build();
    }
}
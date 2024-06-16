package com.project1.oauth.service;

import com.project1.oauth.domain.CommentDomain;
import com.project1.oauth.domain.PostDomain;
import com.project1.oauth.domain.UserDomain;
import com.project1.oauth.repository.CommentRepository;
import com.project1.oauth.repository.PostRepository;
import com.project1.oauth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public List<CommentDomain> getCommentsByPostId(int postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    public Optional<CommentDomain> getCommentById(int commentId) {
        return commentRepository.findById(commentId);
    }

    public CommentDomain createComment(String email, int postId, CommentDomain comment) {
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));
        PostDomain post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public CommentDomain updateComment(int commentId, String email, CommentDomain commentDetails) {
        CommentDomain comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        if (!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("User not authorized to update this comment");
        }

        comment.setContent(commentDetails.getContent());
        return commentRepository.save(comment);
    }

    public void deleteComment(int commentId, String email) {
        CommentDomain comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));

        if (!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("User not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }
}
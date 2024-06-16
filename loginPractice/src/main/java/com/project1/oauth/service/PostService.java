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
public class PostService {
	
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    public List<PostDomain> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<PostDomain> getPostById(int id) { //상세조회 
        return postRepository.findById(id);
    }

    public PostDomain createPost(String email, PostDomain post) { //게시글 생성 
    	System.out.println("게시글 생성 postService 확인");
    	System.out.println("이메일 : "+email);
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));
        post.setUser(user); // user 저장 
        return postRepository.save(post); // 저장
    }

    public PostDomain updatePost(int id, String email, PostDomain postDetails) { //수정 
        PostDomain post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));
        post.setUser(user); // Optional: If you want to re-assign the post to a different user
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setImage(postDetails.getImage());
        return postRepository.save(post);
    }

    public void deletePost(int id, String email) {
    	
        PostDomain post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        UserDomain user = userRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user email"));
        if (!post.getUser().equals(user)) {
            throw new IllegalArgumentException("User not authorized to delete this post");
        }
        System.out.println("어디여");
        System.out.println(post.toString());
        List<CommentDomain> comments = commentRepository.findAll();
        for(int i = 0; i < comments.size(); i++) {
        	commentRepository.delete(comments.get(i));
        }
        
        postRepository.delete(post);
    }
}
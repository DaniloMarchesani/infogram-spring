package com.infogram.service;

import org.springframework.data.domain.Page;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.infogram.models.Post;
import com.infogram.models.User;

import com.infogram.repository.PostRepository;

import jakarta.validation.constraints.NotNull;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;


    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findByUser(User user, Pageable pageable){
        return postRepository.findByUser(user, pageable);
    }

    public Page<Post> findByProfile(User user, Pageable pageable){
        return postRepository.findByUser(user, pageable);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public void deletePost(Long postId) {
        //commentService.deleteCommentsByPost(post);
        postRepository.deleteById(postId);
    }
    
    public Post addPost(Post post) {
        if(post == null) {
            return null;
        }
        return postRepository.save(post);
    }

    public Post updatePost(@NotNull Post post) {
        return postRepository.save(post);
    }
}

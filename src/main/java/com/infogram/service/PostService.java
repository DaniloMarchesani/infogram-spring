package com.infogram.service;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.infogram.models.Post;
import com.infogram.models.User;

import com.infogram.repository.PostRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    public Page<Post> findByUser(User user, Pageable pageable){
        return postRepository.findByUser(user, pageable);
    }

    public Page<Post> findByProfile(User user, Pageable pageable){
        return postRepository.findByUser(user, pageable);
    }
    
}

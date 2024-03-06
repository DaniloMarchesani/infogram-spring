package com.infogram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infogram.models.Comment;
import com.infogram.models.Post;
import com.infogram.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Page<Comment> findByPost(Post post, Pageable pageable){
        return commentRepository.findByPostId(post.getId(), pageable);
    }
}

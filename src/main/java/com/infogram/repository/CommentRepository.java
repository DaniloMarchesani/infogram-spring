package com.infogram.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    Page<Comment> findByPostId(Long postId, Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);
}
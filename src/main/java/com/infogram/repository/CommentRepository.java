package com.infogram.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    Optional<Page<Comment>> findByPostId(Long postId, Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);

}

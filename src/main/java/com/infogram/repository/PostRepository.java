package com.infogram.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.Post;
import com.infogram.models.User;



public interface PostRepository extends JpaRepository<Post, Long>{
    
    Page<Post> findByUser(User user, Pageable pageable);

    
}

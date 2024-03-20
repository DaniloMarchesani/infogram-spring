package com.infogram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infogram.models.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>{
    public List<Resource> findResourceByPostId(Long postId);
}

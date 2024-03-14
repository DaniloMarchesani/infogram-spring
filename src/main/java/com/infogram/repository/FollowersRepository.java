package com.infogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infogram.models.Followers;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long>{
    

}

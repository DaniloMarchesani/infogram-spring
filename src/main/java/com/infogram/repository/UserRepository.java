package com.infogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String userName);

    Boolean existsByUsername(String userName);

    Boolean existsByEmail(String email);
    
}

package com.infogram.repository;

import org.hibernate.query.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.User;
import java.util.Optional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
    
}

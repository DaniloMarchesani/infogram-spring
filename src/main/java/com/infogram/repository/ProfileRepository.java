package com.infogram.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

    Page<Profile> findByFirstName(String firstName, Pageable pageable);

    Page<Profile> findByLastName(String lastName, Pageable pageable);

    Optional<Profile> findByUsername(String username);

    Set<Profile> findProfileByUserId(Long id);

    Page<Profile> findAll(Pageable pageable);

    Optional<Profile> findById(Long id);
    
}
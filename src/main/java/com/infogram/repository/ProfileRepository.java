package com.infogram.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{

    Page<Profile> findByFirstName(String firstName, Pageable pageable);

    Page<Profile> findByLastName(String lastName, Pageable pageable);

    Optional<Profile> findByUserName(String username);

    Page<Profile> findAll(Pageable pageable);

    Optional<Profile> findById(Long id);
    
}

//TODO finire i file della repository e poi andare avanti.
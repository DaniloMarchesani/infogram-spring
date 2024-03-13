package com.infogram.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.infogram.models.Profile;
import com.infogram.repository.ProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public Page<Profile> findByFirstName(String firstName, Pageable pageable){
        return profileRepository.findByFirstName(firstName, pageable);
    }

    public Page<Profile> findByLastName(String lastName, Pageable pageable){
        return profileRepository.findByLastName(lastName, pageable);
    }

    public Optional<Profile> findByUserName(String username){
        return profileRepository.findByUserName(username);
    }

    public Page<Profile> findAll(Pageable pageable){
        return profileRepository.findAll(pageable);
    }

    public Optional<Profile> findById(Long id){
        return profileRepository.findById(id);
    }

    public Profile updatProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id){
        profileRepository.deleteById(id);
    }

}

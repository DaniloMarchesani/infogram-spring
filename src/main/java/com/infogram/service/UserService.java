package com.infogram.service;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogram.models.User;
import com.infogram.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUserName(username);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    
    
}

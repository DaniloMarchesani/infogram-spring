package com.infogram.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infogram.models.User;
import com.infogram.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userService.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        } else {
            return UserDetailsImpl.build(user.get());
        }
        
    }
    
}

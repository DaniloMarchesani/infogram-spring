package com.infogram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Profile;
import com.infogram.models.User;
import com.infogram.payload.request.LoginRequest;
import com.infogram.payload.request.RegistrationRequest;
import com.infogram.payload.response.JwtResponse;
import com.infogram.payload.response.MessageResponse;
import com.infogram.repository.ProfileRepository;
import com.infogram.repository.UserRepository;
import com.infogram.security.service.UserDetailsImpl;
import com.infogram.security.service.jwt.JwtUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;
    
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtils.generateJwtToken(authentication);
            JwtResponse jwtResponse = new JwtResponse(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail());
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Error: Bad credentials " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: Internal server error"));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {

        if (userRepository.existsByUserName(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Username is already taken!"));
        } else if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()));
        Profile profile = new Profile(); //impl the creation of an empty profile as the user register to the app.
        profile.setUser(user);
        profile.setUserName(user.getUserName());

        userRepository.save(user); //save the user to the database
        profileRepository.save(profile); //save the profile to the database

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    

    
}

package com.infogram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Post;
import com.infogram.models.Profile;
import com.infogram.service.CommentService;
import com.infogram.service.PostService;
import com.infogram.service.ProfileService;
import com.infogram.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
public class AppController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;
    
    @GetMapping("/all")
    public String getAll() {
        return "public content";
    }

    @GetMapping("/")
    public String systemCheck() {
        return "System is up and running";
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username, Pageable pageable) {
        try {
            Profile profile = profileService.findByUserName(username);
            profile.setPosts(postService.findByProfile(profile.getUser(), pageable).getContent());
            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Profile not found!");
        }
    }
    
}

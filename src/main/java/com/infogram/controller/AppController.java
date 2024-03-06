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

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    /* 
     * This method returns a complete profile with all the posts and comments
     * of the user with the given username.
     * @author Danilo M.
     *
     */
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

    /* 
     * Create A method to post a new Post!
     */
    /* @PostMapping("/post")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
        try {
            postService.addPost(post);
            return ResponseEntity.ok("Post created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: Post not created!");
        }
    } */
}

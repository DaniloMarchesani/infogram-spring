package com.infogram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Profile;
import com.infogram.models.User;
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
    
    /* EXPLAINATION
     * These two methods taht are following are jsut for checking the servive if it's working properly.
     * so nothing interesting here...
     * 12/03/2024
     */
    @GetMapping("/all")
    public String getAll() {
        return "public content";
    }

    @GetMapping("/")
    public String systemCheck() {
        return "System is up and running";
    }

    /* EXPLENATION
     * This method returns a complete profile with all the posts and comments
     * of the user with the given username.
     * @author Danilo M.
     *
     */
     @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@Valid @PathVariable String username, Pageable pageable) {
        try {
            Optional<Profile> profile = profileService.findByUserName(username);
            if (!profile.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found!");
            }
            profile.get().setPosts(postService.findByProfile(profile.get().getUser(), pageable).getContent());
            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

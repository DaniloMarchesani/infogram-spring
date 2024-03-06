package com.infogram.controller;

import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Post;
import com.infogram.service.PostService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {
    
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String systemCheck() {
        return "Post Controller is up and running";
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(postService.getAll(pageable));
    }

    @PostMapping("/")
    public ResponseEntity<?> addPost(@Valid @RequestBody Post post) {

        try {
            Post newPost = postService.addPost(post);
            if(newPost == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error: Post not created cause is null.");
            }
            return ResponseEntity.ok("Post created successfully with id: " + newPost.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@Valid @PathVariable long id ) {
        Optional<Post> post = postService.findById(id);
        if(post.isPresent()) {
            return ResponseEntity.ok(post.get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error: Post not found");
    }

    // METHOD TO DELETE A POST
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable long id) {
        try {
            Optional<Post> post = postService.findById(id);
            if(!post.isPresent()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error: Post not found");
            }
            postService.deletePost(post.get());
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

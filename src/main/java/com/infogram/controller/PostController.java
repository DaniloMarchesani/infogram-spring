package com.infogram.controller;

import java.util.Optional;

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

import com.infogram.errors.ProfileNotFound;
import com.infogram.models.Post;
import com.infogram.service.CommentService;
import com.infogram.service.ContentService;
import com.infogram.service.PostService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService  contentService;

    @GetMapping("/")
    public String systemCheck() {
        return "Post Controller is up and running";
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(postService.getAll(pageable));
    }

    /* 
     * ROUTE for adding a new post
     * date: 2024-03-13
     * author: @DaniloMarchesani
     */
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

    // METHOD TO GET A POST BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@Valid @PathVariable Long id ) throws ProfileNotFound, Exception{
        /* Optional<Post> post = postService.findById(id);
        if(post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } */
        try {

            Optional<Post> post = contentService.getPostById(id);
            return ResponseEntity.ok(post.get());

        } catch (ProfileNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllUserPosts(@Valid @PathVariable long id, Pageable pageable) throws ProfileNotFound {
        try {
            return ResponseEntity.ok(contentService.getAllProfilePosts(id, pageable));
        } catch (ProfileNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }

    }

    /* 
     * ROUTE for updating a post
     * date: 2024-03-13
     * author: @DaniloMarchesani
     */
    @PutMapping("/")
    public ResponseEntity<?> updatePost(@Valid @RequestBody Post post) {
        try {
            Optional<Post> postToUpdate = postService.findById(post.getId());
            if(!postToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Post not found");
            }

            postToUpdate.get().setTitle(post.getTitle());
            postToUpdate.get().setProfile(post.getProfile());
            postToUpdate.get().setKindOfPost(post.getKindOfPost());
            postToUpdate.get().setCreatedAt(post.getCreatedAt());
            postToUpdate.get().setDescription(post.getDescription());
            postToUpdate.get().setLikes(post.getLikes());
            postToUpdate.get().setUrl(post.getUrl());

            postService.updatePost(postToUpdate.get()); //saving into the db.

            return ResponseEntity.status(HttpStatus.CREATED).body("Post updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // METHOD TO DELETE A POST
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable long id) {
        try {
            Optional<Post> post = postService.findById(id);

            if(!post.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Post not found");
            }
            
            commentService.deleteCommentsByPost(post.get());
            postService.deletePost(id);
            return ResponseEntity.ok("Post deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

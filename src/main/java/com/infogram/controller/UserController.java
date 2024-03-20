package com.infogram.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Post;
import com.infogram.models.Profile;
import com.infogram.models.User;
import com.infogram.service.CommentService;
import com.infogram.service.PostService;
import com.infogram.service.ProfileService;
import com.infogram.service.UserService;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/")
    public ResponseEntity<?> getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(currentPrincipalName).get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }

    /*
     * This method returns a user.
     * If the user is not found, it returns a 404 status code.
     * If there is an error, it returns a 500 status code.
     * 12/03/2024
     * 
     * @param username
     * 
     * @return ResponseEntity<?>
     * date: 12/03/2024
     * autor: Danilo Marchesani
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            Optional<User> user = userService.findByUsername(username);
            if (!user.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(user.get());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }

    /*
     * This method deletes a user.
     * If the user is not found, it returns a 404 status code.
     * If there is an error, it returns a 500 status code.
     * 12/03/2024
     * 
     * @param id
     * 
     * @return ResponseEntity<?>
     * date: 12/03/2024
     * autor: Danilo Marchesani
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Pageable pageable) {
        try {
            if (!userService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            /* List<Post> posts = postService.findByUser(userService.findById(id).get(), pageable).getContent();
            for (Post post : posts) {
                commentService.deleteCommentsByPost(post);
                postService.deletePost(post.getId());
            } */
            Set<Profile> profiles = profileService.findProfileByUserId(id);
            for (Profile profile : profiles) {
                profileService.deleteProfile(profile.getId());
            }
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully! id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}

package com.infogram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin(origins = "*" , maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
public class AppController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostService postService;
    
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
            profile.get().setPosts(postService.findByProfile(profile.get(), pageable).getContent());
            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    /* 
     * CREATE A PROFILE
     * This route will create a new profile for the user with the given username.
     * The body of the request must contain the profile data.
     * date: 12/03/2024
     * author: Danilo M.
     */
    @PostMapping("/") 
    public ResponseEntity<?> createProfile(@RequestBody Profile profile) {

        try {
            Optional<Profile> existingProfile = profileService.findByUserName(profile.getUsername());
            if(existingProfile.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
            }
            profileService.createProfile(profile);
            return ResponseEntity.ok().body("Profile created!" + profile.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error: " + e.getMessage());
        }
    }

    /* UPDATE ROUTE
     * This Route will update a profile with the given id.
     * The body of the request must contain the new profile data.
     * date: 12/03/2024
     * author: Danilo M.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        
        try {
            Optional<Profile> profileToUpdate = profileService.findById(id);

            if(!profileToUpdate.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found!");
            }

            profileToUpdate.get().setUsername(profile.getUsername());
            profileToUpdate.get().setFirstName(profile.getFirstName());
            profileToUpdate.get().setLastName(profile.getLastName());
            profileToUpdate.get().setBio(profile.getBio());
            profileToUpdate.get().setAvatarUrl(profile.getAvatarUrl());
            profileToUpdate.get().setBirthday(profile.getBirthday());

            profileService.updatProfile(profileToUpdate.get());

            return ResponseEntity.ok("Profile updated! " + profileToUpdate.get().getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( "internal server error: " + e.getMessage());
        }
        
    }

    /* 
     * DELETE ROUTE
     * This route will delete a profile with the given id.
     * date: 12/03/2024
     * author: Danilo M.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        try {
            Optional<Profile> profile = profileService.findById(id);
            if (!profile.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found!");
            }
            profileService.deleteProfile(id);
            return ResponseEntity.ok("Profile deleted!" + profile.get().getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error: " + e.getMessage());
        }
    }
}

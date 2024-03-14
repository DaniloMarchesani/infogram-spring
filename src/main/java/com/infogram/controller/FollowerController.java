package com.infogram.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Followers;
import com.infogram.models.Profile;
import com.infogram.service.FollowersService;
import com.infogram.service.ProfileService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/follower")
public class FollowerController {
    
    @Autowired
    private FollowersService followersService;
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/all")
    public List<Followers> getAllFollowers() {
        return null;
    }

    /* 
     * EXPLANATION:
     * This Route returns a list of profiles that follows my profile.
     * @param id: The id of the profile that I want to get the followers from.
     * @return ResponseEntity<?>: If the profile is found, it returns a list of followers, otherwise it returns a 404 status code.
     * date: 15/03/2024
     * author: Danilo Marchesani
     *      
     * */
    @GetMapping("/following/{id}")
    public ResponseEntity<?> getFollowing(@PathVariable Long id) {
        Optional<Profile> profile = profileService.findById(id);
        if (profile.isPresent()) {
            Optional<List<Followers>> followers = followersService.getFollowing(profile.get());
            if(!followers.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No followers found");
            }
            if (followers.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't have any followers");
            }
            return ResponseEntity.ok(followers.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            
        }
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<?> getFollower(@PathVariable Long id) {
        try {
            Optional<Profile> profile = profileService.findById(id);
            if(!profile.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            }
            Optional<List<Followers>> followers = followersService.getFollower(profile.get());
            if(!followers.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No followers found");
            }
            if (followers.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user doesn't have any followers");
            }
            return ResponseEntity.ok(followers.get());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
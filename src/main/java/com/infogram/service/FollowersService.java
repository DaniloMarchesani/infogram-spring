package com.infogram.service;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogram.models.Followers;
import com.infogram.models.Profile;
import com.infogram.repository.FollowersRepository;

@Service
public class FollowersService {
    
    @Autowired
    private FollowersRepository followersRepository;


    //METHOD TO FOLLOW ANOTHER PROFILE
    public Followers follow(Profile follower, Profile following) {
        Followers newFollower = new Followers();
        newFollower.setFollower(follower);
        newFollower.setFollowing(following);
        return followersRepository.save(newFollower);
    }

    /* 
     * EXPLANATION:
     * This method returns a list of profiles that follows a specific profile.
     * @param profile: The profile that I want to get the followers from.
     * @return Optional<List<Followers>>: If the profile is found, it returns a list of followers, otherwise it returns an empty list.
     * date: 15/03/2024
     * author: Danilo Marchesani
     */
    //OK: This method is well implemented
    public Optional<List<Followers>> getFollowing(Profile profile) {
        return followersRepository.findByFollowing(profile);
    }

    public Optional<List<Followers>> getFollower(Profile follower) {
        return followersRepository.findByFollower(follower);
    }

    /* 
     * EXPLANATION:
     * This method saves a new follower in the database.
     * @param follower: The follower that I want to save.
     * This is still in develop
     * 
     * 
     */
    public Followers saveFollower(Followers follower) {
        return followersRepository.save(follower);
    }
    
    public void deleteFollower(Long id) {
        followersRepository.deleteById(id);
    }


}

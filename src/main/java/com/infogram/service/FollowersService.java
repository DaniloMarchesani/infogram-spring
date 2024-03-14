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

    public Optional<List<Followers>> getFollowing(Profile profile) {
        return followersRepository.findByFollowing(profile);
    }

    public Followers saveFollower(Followers follower) {
        return followersRepository.save(follower);
    }

    public void deleteFollower(Long id) {
        followersRepository.deleteById(id);
    }


}

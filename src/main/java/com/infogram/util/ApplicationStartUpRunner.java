package com.infogram.util;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.infogram.models.Comment;
import com.infogram.models.Post;
import com.infogram.models.Profile;
import com.infogram.models.User;
import com.infogram.repository.CommentRepository;
import com.infogram.repository.PostRepository;
import com.infogram.repository.ProfileRepository;
import com.infogram.repository.UserRepository;
import com.infogram.service.FollowersService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApplicationStartUpRunner implements CommandLineRunner{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FollowersService followersService;
    
    

    @Override
    public void run(String... args) throws Exception {
        
       createDummyData();
    }
    

    public void createDummyData() {
        log.info("Creating dummy data");
        // Create a user
        User user = new User();
        user.setEmail("danilo@fakemail.com");
        user.setPassword("12345678");
        user.setUsername("danilo95");
        userRepository.save(user);

        //create a user2
        User user2 = new User();
        user2.setEmail("pippo@coldmail.com");
        user2.setUsername("pippo");
        user2.setPassword("12345678");
        userRepository.save(user2);

        //create a user2
        User user3 = new User();
        user3.setEmail("salame@hotmail.com");
        user3.setUsername("salamino8585");
        user3.setPassword("12345678");
        userRepository.save(user3);

        //create a user2
        User user4 = new User();
        user4.setEmail("metallica12@gmail.com");
        user4.setUsername("metallarocronico");
        user4.setPassword("12345678");
        userRepository.save(user4);

        // Create a profile
        Profile profile = new Profile();
        profile.setFirstName("Danilo");
        profile.setLastName("Marchesani");
        profile.setUsername(user.getUsername());
        profile.setBio("I'm a software developer");
        profile.setBirthday(LocalDateTime.of(1995, 10, 10, 0, 0));
        profile.setUser(user);
        profileRepository.save(profile);

        Profile profile2 = new Profile();
        profile2.setFirstName("Sivlio");
        profile2.setLastName("Berlusconi");
        profile2.setUsername(user2.getUsername());
        profile2.setBio("I'm a billionair");
        profile2.setBirthday(LocalDateTime.of(1956, 10, 10, 0, 0));
        profile2.setUser(user2);
        profileRepository.save(profile2);

        Profile profile3 = new Profile();
        profile3.setFirstName("Paolo");
        profile3.setLastName("Barbera");
        profile3.setUsername(user3.getUsername());
        profile3.setBio("I'm a retard");
        profile3.setBirthday(LocalDateTime.of(1956, 10, 10, 0, 0));
        profile3.setUser(user3);
        profileRepository.save(profile3);

        Profile profile4 = new Profile();
        profile4.setFirstName("cosimo");
        profile4.setLastName("curlante");
        profile4.setUsername(user4.getUsername());
        profile4.setBio("I'm a UX DESIGNER");
        profile4.setBirthday(LocalDateTime.of(1956, 10, 10, 0, 0));
        profile4.setUser(user4);
        profileRepository.save(profile4);

        // Create a post
        Post post = new Post();
        post.setTitle("First Post");
        post.setDescription("This is the first post");
        post.setUrl("https://www.google.com");
        post.setProfile(profile);
        postRepository.save(post);
        // Create a post

        // Create a post
        Post post2 = new Post();
        post2.setTitle("First Post");
        post2.setDescription("This is the first post");
        post2.setUrl("https://www.google.com");
        post2.setProfile(profile);
        postRepository.save(post2);

        // Create a post
        Post post3 = new Post();
        post3.setTitle("First Post");
        post3.setDescription("This is the first post");
        post3.setUrl("https://www.google.com");
        post3.setProfile(profile);
        postRepository.save(post3);


        // Create a comment
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText("This is the first comment");
        commentRepository.save(comment);

        //create a relationship between users
        followersService.follow(profile, profile2);
        followersService.follow(profile, profile3);
        followersService.follow(profile3, profile4);
        followersService.follow(profile2, profile4);
    
}
}

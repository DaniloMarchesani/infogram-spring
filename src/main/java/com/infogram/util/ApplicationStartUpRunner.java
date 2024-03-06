package com.infogram.util;

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
        user.setUserName("danilo95");
        userRepository.save(user);

        //create a user2
        User user2 = new User();
        user2.setEmail("pippo@coldmail.com");
        user2.setUserName("pippo");
        user2.setPassword("12345678");
        userRepository.save(user2);

        // Create a profile
        Profile profile = new Profile();
        profile.setFirstName("Danilo");
        profile.setLastName("Silva");
        profile.setUser(user);
        profileRepository.save(profile);


        // Create a post
        Post post = new Post();
        post.setTitle("First Post");
        post.setDescription("This is the first post");
        post.setUrl("https://www.google.com");
        post.setUser(user);
        postRepository.save(post);

        // Create a comment
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText("This is the first comment");
        commentRepository.save(comment);

        //create a relationship between users
        

        
}
}

package com.infogram.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infogram.errors.PostNotFound;
import com.infogram.errors.ProfileNotFound;
import com.infogram.models.Post;
import com.infogram.models.Profile;

/* 
 * This class is a service for the content of the application.
 * This Service is made to build a Post with its resources and data and serve irt to the client.
 * 
 */
@Service
public class ContentService {
    
    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FileService fileService;

    public Optional<Post> getPostById(Long id) throws PostNotFound, Exception {
        try {
            Optional<Post> post = postService.findById(id);
            if(post.isEmpty()) {
                throw new PostNotFound("Post not found");
            }
            post.get().setResources(fileService.findResourcesByPostId(post.get().getId()));
            return post;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Page<Post> getAllProfilePosts(Long Id, Pageable pageable) throws ProfileNotFound {
        Optional<Profile> profile = profileService.findById(Id);
        if(profile.isEmpty()) {
            throw new ProfileNotFound("Profile not found");
        }
        Page<Post> posts = postService.findByProfile(profile.get(), pageable);
        
        posts.getContent().forEach( post -> {
            post.setResources(fileService.findResourcesByPostId(post.getId()));
        });
        return posts;
    }
}

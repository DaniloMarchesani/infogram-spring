package com.infogram.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.infogram.errors.PostNotFound;
import com.infogram.errors.ProfileNotFound;
import com.infogram.models.KindOfResource;
import com.infogram.models.Post;
import com.infogram.models.Profile;
import com.infogram.models.Resource;
import com.infogram.repository.ResourceRepository;

@Service
public class FileService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    public static final String UPLOAD_DIR = "uploads";

    public List<Resource> findResourcesByPostId(Long postId) {
        return resourceRepository.findResourceByPostId(postId);
    }

    public void uploadFile(MultipartFile file, String profileUsername, Long postId) throws ProfileNotFound, PostNotFound {

        if (!new File(UPLOAD_DIR).exists()) {
            new File(UPLOAD_DIR).mkdir();
        }

        Optional<Post> post = postService.findById(postId);
        if(!post.isPresent()) {
            throw new PostNotFound("Post not found or not exist!!");
        }

        Optional<Profile> profile = profileService.findByUserName(profileUsername);
        if(!profile.isPresent()) {
            throw new ProfileNotFound("Profile not found!");
        }

        Resource resource = new Resource();
        resource.setUrl(UPLOAD_DIR + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
        resource.setType(KindOfResource.IMG);
        resource.setPost(post.get());
        resource.setCreatedAt(LocalDateTime.now());
        

        try {
            resourceRepository.save(resource);
            Path copyLocation = Paths
                    .get(UPLOAD_DIR + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
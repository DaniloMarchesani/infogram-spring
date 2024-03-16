package com.infogram.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.infogram.models.KindOfResource;
import com.infogram.models.Resource;
import com.infogram.repository.ResourceRepository;

@Service
public class FileService {

    @Autowired
    private ResourceRepository resourceRepository;

    public static final String UPLOAD_DIR = "uploads";

    public void uploadFile(MultipartFile file) {

        if (!new File(UPLOAD_DIR).exists()) {
            new File(UPLOAD_DIR).mkdir();
        }
        Resource resource = new Resource();
        resource.setUrl(UPLOAD_DIR + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
        resource.setType(KindOfResource.IMG);
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
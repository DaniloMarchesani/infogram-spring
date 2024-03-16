package com.infogram.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

    public static final String UPLOAD_DIR = "uploads";

    public void uploadFile(MultipartFile file, String name) {

        if(!new File(UPLOAD_DIR).exists()) {
            new File(UPLOAD_DIR).mkdir();
        }

        try{

            Path copyLocation = Paths.get(UPLOAD_DIR + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();        
    }
}

}
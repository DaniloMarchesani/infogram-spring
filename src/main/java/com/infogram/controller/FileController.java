package com.infogram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infogram.errors.PostNotFound;
import com.infogram.errors.ProfileNotFound;
import com.infogram.service.FileService;

@Validated
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/file")
public class FileController {
    
    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public ResponseEntity<?> checkSystem() {
        return ResponseEntity.status(HttpStatus.OK).body("File Controller is up and running");
    }


    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> upload(@Validated @RequestParam("file") MultipartFile file, @RequestParam("post") Long postId, @RequestParam("profile") String username) throws ProfileNotFound, PostNotFound {
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            fileService.uploadFile(file, username, postId);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        }
        catch (ProfileNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (PostNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }

    //TEST-UPLOAD AVATAR
    @PostMapping(path = "/upload/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("profile") String username) throws ProfileNotFound {
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
        }

        try {
            fileService.uploadAvatar(file, username);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        }
        catch (ProfileNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }
    
}

package com.infogram.errors;

import lombok.Data;

@Data
public class ProfileNotFound extends Exception{
    private String message;

    public ProfileNotFound(String message) {
        this.message = message;
    }
}

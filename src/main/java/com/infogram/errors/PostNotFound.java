package com.infogram.errors;

public class PostNotFound extends Exception{
    
    private String message;

    public PostNotFound(String message) {
        this.message = message;
    }

}

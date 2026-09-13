package com.lwc.blogapi;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String id) {
        super("Post with id '" + id + "' not found");
    }
}
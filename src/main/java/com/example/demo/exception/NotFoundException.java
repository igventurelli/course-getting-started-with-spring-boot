package com.example.demo.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Resource not found for id " + id);
    }
}
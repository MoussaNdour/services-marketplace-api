package com.example.marketplace.exception;

public class NonexistingImageException extends RuntimeException {
    public NonexistingImageException(String message) {
        super(message);
    }
}

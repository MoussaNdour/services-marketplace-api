package com.example.marketplace.exception;

public class AskingServiceNotFoundException extends RuntimeException {
    public AskingServiceNotFoundException(String message) {
        super(message);
    }
}

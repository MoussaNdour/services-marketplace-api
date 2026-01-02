package com.example.marketplace.exception;

public class EmailAlreadyUserException extends RuntimeException {
    public EmailAlreadyUserException(String message) {
        super(message);
    }
}

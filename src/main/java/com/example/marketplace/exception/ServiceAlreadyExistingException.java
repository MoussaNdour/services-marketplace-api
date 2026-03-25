package com.example.marketplace.exception;

public class ServiceAlreadyExistingException extends RuntimeException {
    public ServiceAlreadyExistingException(String message) {
        super(message);
    }
}

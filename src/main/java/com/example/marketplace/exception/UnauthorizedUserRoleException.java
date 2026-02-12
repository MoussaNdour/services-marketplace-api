package com.example.marketplace.exception;

public class UnauthorizedUserRoleException extends RuntimeException {
    public UnauthorizedUserRoleException(String message) {
        super(message);
    }
}

package com.example.marketplace.exception;

public class ServiceProposalNotFoundException extends RuntimeException {
    public ServiceProposalNotFoundException(String message) {
        super(message);
    }
}

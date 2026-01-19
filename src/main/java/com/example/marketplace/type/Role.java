package com.example.marketplace.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, CLIENT, PROVIDER;

    @JsonCreator
    public static Role fromString(String key) {
        return key == null ? null : Role.valueOf(key.toUpperCase());
    }
}

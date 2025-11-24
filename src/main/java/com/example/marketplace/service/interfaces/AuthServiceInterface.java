package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.Login;
import com.example.marketplace.dto.request.UserRequest;

public interface AuthServiceInterface {

    void login(Login user);

    void registerUser(UserRequest user);

    boolean checkUserEmail(String email);
}

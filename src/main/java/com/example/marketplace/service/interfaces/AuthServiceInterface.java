package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.*;

import com.example.marketplace.dto.response.*;
import com.example.marketplace.entity.User;

import java.util.List;


public interface AuthServiceInterface {

    boolean isEmailFree(String email);

    ClientResponseDTO registerClient(ClientRequestDTO client);

    ProviderRespoonseDTO registerProvider(ProviderRequestDTO provider);

    LoginResponseDTO connect(Login login);

    String refreshToken(User user);

    Object registerUser(RegistrationRequest request);
}

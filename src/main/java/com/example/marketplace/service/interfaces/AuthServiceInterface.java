package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.*;

import com.example.marketplace.dto.response.*;
import com.example.marketplace.entity.User;


public interface AuthServiceInterface {

    boolean isEmailFree(String email);

    ClientResponseDTO registerClient(ClientRequestDTO client);

    ProviderResponseDTO registerProvider(ProviderRequestDTO provider);

    LoginResponseDTO connect(Login login);

    String refreshToken(Payload payload);

    Object registerUser(RegistrationRequest request);
}

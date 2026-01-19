package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.AdminRequestDTO;
import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.dto.response.UserResponseDTO;
import com.example.marketplace.entity.User;

import java.util.List;

public interface AuthServiceInterface {

    boolean isEmailFree(String email);

    ClientResponseDTO registerClient(ClientRequestDTO client);

    ProviderRespoonseDTO registerProvider(ProviderRequestDTO provider);

    AdminResponseDTO registerAdmin(AdminRequestDTO admin);

    ClientResponseDTO findClient(String email);

    ProviderRespoonseDTO findProvider(String email);

    AdminResponseDTO findAdmin(String email);

    List<UserResponseDTO> getAllUsers();
}

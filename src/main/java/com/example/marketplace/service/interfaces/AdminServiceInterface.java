package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.AdminRequestDTO;
import com.example.marketplace.dto.response.AdminResponseDTO;

public interface AdminServiceInterface extends GeneralInterface<AdminRequestDTO, AdminResponseDTO>{

    void disableAccount(String email);
    AdminResponseDTO getByEmail(String email);
}

package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;

import java.util.List;

public interface ProviderServiceInterface extends GeneralInterface<ProviderRequestDTO, ProviderResponseDTO>{

    void disableAccount(String email);
    ProviderResponseDTO getByEmail(String email);
    List<ProviderResponseDTO> getProvidersByServiceId(int id);
}

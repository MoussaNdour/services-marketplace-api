package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Service;
import com.example.marketplace.entity.User;

import java.util.List;

public interface ProviderServiceInterface extends GeneralInterface<ProviderRequestDTO, ProviderResponseDTO>{

    void disableAccount(String email);
    ProviderResponseDTO getByEmail(String email);
    List<ServiceResponseDTO> getAllServicesByProvider(User user,String email);

}

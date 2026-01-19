package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;

public interface ClientServiceInterface extends GeneralInterface<ClientRequestDTO,ClientResponseDTO> {

    void disableAccount(String email);
    ClientResponseDTO getByEmail(String email);

}

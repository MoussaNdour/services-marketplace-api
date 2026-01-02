package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;

public interface ProviderServiceInterface extends GeneralInterface<ProviderRequestDTO, ProviderRespoonseDTO>{

    void disableAccount(String email);
    ProviderRespoonseDTO getByEmail(String email);
}

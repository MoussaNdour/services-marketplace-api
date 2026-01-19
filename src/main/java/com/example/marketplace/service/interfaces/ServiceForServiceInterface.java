package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Provider;
import java.util.List;

public interface ServiceForServiceInterface extends GeneralInterface<ServiceRequestDTO, ServiceResponseDTO>{

    List<ProviderRespoonseDTO> getProvidersByServiceId(int serviceid);
}

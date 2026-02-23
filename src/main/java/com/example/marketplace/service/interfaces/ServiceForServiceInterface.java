package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.ServiceProposal;

import java.util.List;

public interface ServiceForServiceInterface extends GeneralInterface<ServiceRequestDTO, ServiceResponseDTO>{

    List<ServiceResponseDTO> searchService(String name);

    List<ServiceProposalResponseDTO> getProposalsByServiceId(int id);

    List<ProviderResponseDTO> getProvidersByServiceId(int id);
}

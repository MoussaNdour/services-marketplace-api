package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.request.ServiceProposalUpdateDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.User;
import java.util.List;

public interface ServiceProposalServiceInterface extends GeneralInterface<ServiceProposalRequestDTO, ServiceProposalResponseDTO>{
    List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider);
    ProviderResponseDTO getProviderOfProposal(int id);
    ServiceResponseDTO getServiceOfProposal(int id);
    ServiceProposalResponseDTO updateService(int id, ServiceProposalUpdateDTO update);
}

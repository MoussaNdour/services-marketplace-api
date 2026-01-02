package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import java.util.List;

public interface ServiceProposalServiceInterface extends GeneralInterface<ServiceProposalRequestDTO, ServiceProposalResponseDTO>{
    Provider checkProvider(String email);
    void saveServiceProposal(ServiceProposalRequestDTO dto, User user);
    List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider);
}

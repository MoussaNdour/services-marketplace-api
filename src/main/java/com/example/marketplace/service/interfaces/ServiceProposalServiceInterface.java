package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.User;
import java.util.List;

public interface ServiceProposalServiceInterface extends GeneralInterface<ServiceProposalRequestDTO, ServiceProposalResponseDTO>{
    List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider);
    ServiceProposalResponseDTO getServiceProposalByServiceIdAndProviderId(int serviceid, int providerid);
    List<ServiceProposalResponseDTO> getServiceProposalsByServiceId(int serviceId);
}

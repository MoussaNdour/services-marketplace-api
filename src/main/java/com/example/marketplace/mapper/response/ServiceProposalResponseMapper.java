package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.ServiceProposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceProposalResponseMapper {

    ServiceProposalResponseDTO toDTO(ServiceProposal service);
}

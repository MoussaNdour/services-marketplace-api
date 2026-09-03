package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.entity.ServiceProposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceProposalRequestMapper {

    @Mapping(target = "service",ignore = true)
    @Mapping(target = "provider", ignore = true)
    ServiceProposal toEntity(ServiceProposalRequestDTO dto);
}

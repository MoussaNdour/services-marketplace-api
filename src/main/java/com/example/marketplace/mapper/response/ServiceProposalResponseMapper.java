package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.ServiceProposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceProposalResponseMapper {

    @Mapping(source = "service.name", target = "serviceName")
    @Mapping(source = "provider.user.email", target = "providerEmail")
    @Mapping(source = "provider.firstname", target = "providerFirstName")
    @Mapping(source = "provider.lastname", target = "providerLastName")
    @Mapping(source = "service.description", target="description")
    @Mapping(source = "service.category.name", target="serviceCategory")
    ServiceProposalResponseDTO toDTO(ServiceProposal service);
}

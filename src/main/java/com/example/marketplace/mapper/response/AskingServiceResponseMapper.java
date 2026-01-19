package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.AskingServiceResponseDTO;
import com.example.marketplace.entity.AskingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AskingServiceResponseMapper {

    @Mapping(source = "proposal.service.name", target = "serviceName")
    @Mapping(source = "client.firstname", target = "clientFirstName")
    @Mapping(source = "client.lastname", target = "clientLastName")
    AskingServiceResponseDTO toDTO(AskingService entity);
}

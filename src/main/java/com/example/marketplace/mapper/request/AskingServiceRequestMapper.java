package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.AskingServiceRequestDTO;
import com.example.marketplace.entity.AskingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AskingServiceRequestMapper {

    @Mapping(source = "idserviceproposal", target = "proposal.id")
    @Mapping(source = "idclient", target = "client.id")
    AskingService toEntity(AskingServiceRequestDTO dto);
}

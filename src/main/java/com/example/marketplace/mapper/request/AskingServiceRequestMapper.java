package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.entity.Asking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AskingServiceRequestMapper {

    @Mapping(source = "idserviceproposal", target = "proposal.id")
    @Mapping(source = "idclient", target = "client.id")
    Asking toEntity(AskingRequestDTO dto);
}

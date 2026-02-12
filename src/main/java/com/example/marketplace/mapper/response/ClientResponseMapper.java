package com.example.marketplace.mapper.response;

import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper {

    @Mapping(source = "user.email", target = "email")
    ClientResponseDTO toDTO(Client client);
}

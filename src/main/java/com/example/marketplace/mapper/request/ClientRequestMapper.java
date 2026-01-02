package com.example.marketplace.mapper.request;

import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {
    @Mapping(target = "user", ignore = true)
    Client toEntity(ClientRequestDTO dto);
}

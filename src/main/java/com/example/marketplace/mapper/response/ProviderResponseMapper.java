package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProviderResponseMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    ProviderResponseDTO toDTO(Provider provider);
}

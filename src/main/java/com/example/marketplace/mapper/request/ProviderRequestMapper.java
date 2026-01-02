package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProviderRequestMapper {

    @Mapping(target = "user", ignore = true)
    Provider toEntity(ProviderRequestDTO dto);
}

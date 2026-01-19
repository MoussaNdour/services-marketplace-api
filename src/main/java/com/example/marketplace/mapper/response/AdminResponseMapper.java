package com.example.marketplace.mapper.response;

import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminResponseMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    AdminResponseDTO toDTO(Admin admin);
}

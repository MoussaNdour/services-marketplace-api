package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.AdminRequestDTO;
import com.example.marketplace.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminRequestMapper {

    @Mapping(target = "user", ignore = true)
    Admin toEntity(AdminRequestDTO dto);
}

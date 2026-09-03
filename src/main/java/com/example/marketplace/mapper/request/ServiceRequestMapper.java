package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

    @Mapping(source = "idcategory", target = "category.id")
    @Mapping(target = "image", ignore = true)
    Service toEntity(ServiceRequestDTO dto);
}

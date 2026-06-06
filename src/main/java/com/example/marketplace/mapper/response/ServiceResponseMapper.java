package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Category;
import com.example.marketplace.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper {

    @Mapping(source = "image.path", target = "imagePath")
    ServiceResponseDTO toDTO(Service service);
}

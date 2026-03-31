package com.example.marketplace.mapper.response;


import com.example.marketplace.dto.response.AskingResponseDTO;
import com.example.marketplace.entity.Asking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AskingServiceResponseMapper {
    AskingResponseDTO toDTO(Asking entity);
}

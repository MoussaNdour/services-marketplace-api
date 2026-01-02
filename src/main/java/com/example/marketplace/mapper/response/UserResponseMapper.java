package com.example.marketplace.mapper.response;

import com.example.marketplace.dto.response.UserResponseDTO;
import com.example.marketplace.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseDTO toDTO(User user);
}

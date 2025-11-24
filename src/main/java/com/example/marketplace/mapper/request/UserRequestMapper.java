package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.UserRequest;
import com.example.marketplace.entity.User;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    User toEntity(UserRequest user);
}

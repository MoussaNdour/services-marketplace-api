package com.example.marketplace.mapper.response;

import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponseDTO toDTO(Category category);
}

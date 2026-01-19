package com.example.marketplace.mapper.request;

import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {

    Category toEntity(CategoryRequestDTO category);
}

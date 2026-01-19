package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;


public interface CategoryServiceInterface extends GeneralInterface<CategoryRequestDTO, CategoryResponseDTO>{

    CategoryResponseDTO getByName(String name);

}

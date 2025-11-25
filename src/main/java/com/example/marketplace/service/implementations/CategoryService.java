package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.entity.Category;
import com.example.marketplace.mapper.request.CategoryRequestMapper;
import com.example.marketplace.mapper.response.CategoryResponseMapper;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.service.interfaces.CategoryServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceInterface {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryRequestMapper categoryRequestMapper;

    @Autowired
    CategoryResponseMapper categoryResponseMapper;

    @Override
    public CategoryResponseDTO getByName(String name) {
        return null;
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO dto) {

        Category category=categoryRepository.save(categoryRequestMapper.toEntity(dto));

        return categoryResponseMapper.toDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAll() {

        List<CategoryResponseDTO> categories=new ArrayList();

        for(Category category:categoryRepository.findAll())
        {
            categories.add(categoryResponseMapper.toDTO(category));
        }

        return categories;
    }

    @Override
    public CategoryResponseDTO getById(int id) {
        Category category=categoryRepository.findById(id).orElse(null);

        if(category==null)
            return null;
        else{
            return categoryResponseMapper.toDTO(category);
        }
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}

package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.entity.Category;
import com.example.marketplace.exception.CategoryAlreadyExistException;
import com.example.marketplace.exception.CategoryNotFoundException;
import com.example.marketplace.mapper.request.CategoryRequestMapper;
import com.example.marketplace.mapper.response.CategoryResponseMapper;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.service.interfaces.CategoryServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if(categoryRepository.findByName(dto.getName())!=null)
            throw new CategoryAlreadyExistException("This category already exist");
        else{
            CategoryResponseDTO response=categoryResponseMapper.toDTO(categoryRepository.save(categoryRequestMapper.toEntity(dto)));
            Map<String,Map<String,String>> links=new HashMap<>();
            Map<String,String> self=new HashMap<>();
            self.put("href","categories/"+response.getId());

            links.put("self",self);
            response.set_links(links);

            return response;
        }
    }

    @Override
    public List<CategoryResponseDTO> getAll() {

        List<CategoryResponseDTO> categories=new ArrayList();

        for(Category category:categoryRepository.findAll())
        {
            CategoryResponseDTO response=categoryResponseMapper.toDTO(category);
            Map<String,Map<String,String>> links=new HashMap<>();
            Map<String,String> self=new HashMap<>();
            self.put("href","categories/"+response.getId());

            links.put("self",self);

            response.set_links(links);

            categories.add(response);
        }

        return categories;
    }

    @Override
    public CategoryResponseDTO getById(int id) {
        Category category=categoryRepository.findById(id).orElse(null);

        if (category==null)
            throw new CategoryNotFoundException("There no category with this id");
        else{
            CategoryResponseDTO response=categoryResponseMapper.toDTO(category);

            Map<String,Map<String,String>> links=new HashMap<>();
            Map<String,String> self=new HashMap<>();
            self.put("href","categories/"+response.getId());

            links.put("self",self);
            response.set_links(links);

            return response;
        }
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }


}

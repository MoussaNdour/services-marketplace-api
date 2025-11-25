package com.example.marketplace.controller;


import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.service.interfaces.CategoryServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api/category")
public class Category {

    @Autowired
    CategoryServiceInterface service;

    @PostMapping("")
    public ResponseEntity create(@RequestBody @Valid CategoryRequestDTO category)
    {
        CategoryResponseDTO dto =service.save(category);

        if(dto!=null)
            return ResponseEntity.ok(dto);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(service.getAll());
    }

}

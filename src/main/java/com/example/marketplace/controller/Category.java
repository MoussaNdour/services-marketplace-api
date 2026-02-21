package com.example.marketplace.controller;


import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.service.interfaces.CategoryServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api")
public class Category {

    @Autowired
    CategoryServiceInterface service;

    @Operation(
            summary = "Create service",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "Accordingly to REST principles, this endpoint is for creating a category and need to be an admin and service provider",
            responses = {
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthenticated "
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Unauthorized to create a new Category"
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created the category"
                    )
            }
    )
    @PostMapping("/categories")
    public ResponseEntity create(@RequestBody @Valid CategoryRequestDTO category)
    {
        return ResponseEntity.ok(service.save(category));
    }

    @Operation(
            summary = "Get All Categories",
            security = {  },
            description = "This endpoint is for retrieving all existing services and just need to be authenticated",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successfully retrieved categories"
                    )
            }
    )
    @GetMapping("/public/categories")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "",
            description = "",
            security = {},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved category"
                    )
            }
    )
    @GetMapping("/public/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable int id){

        return ResponseEntity.ok(service.getById(id));
    }
}

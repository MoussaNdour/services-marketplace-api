package com.example.marketplace.controller;


import com.example.marketplace.dto.request.CategoryRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.service.interfaces.CategoryServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @PostMapping("")
    public ResponseEntity create(@RequestBody @Valid CategoryRequestDTO category)
    {
        CategoryResponseDTO dto =service.save(category);

        if(dto!=null)
            return ResponseEntity.ok(dto);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Operation(
            summary = "Get All Categories",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "This endpoint is for retrieving all existing services and just need to be authenticated",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successfully retrieved categories"
                    )
            }
    )
    @GetMapping("")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(service.getAll());
    }

}

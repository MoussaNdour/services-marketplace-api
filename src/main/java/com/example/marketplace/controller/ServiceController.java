package com.example.marketplace.controller;


import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ServiceController {

    @Autowired
    ServiceForServiceInterface service;


    @Operation(
            summary = "Return all services",
            description = "Retrieve all services from the database and return them to the user as a list of services that can be blank.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Services successfully retrieved from the database",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/public/services")
    public ResponseEntity getAllServices(@RequestParam(required = false) String name){
        if (name != null && !name.isEmpty()){
            return ResponseEntity.ok(service.searchService(name));
        }
        return ResponseEntity.ok(service.getAll());
    }



    @Operation(
            summary = "retrieve service by his id",
            description = "Check if the service exist in the database by using his id and return it, if it exist.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Service successfully retrieved",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Service not found",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping("/public/services/{id}")
    public ResponseEntity getServiceById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(
            summary = "Create a service",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "Create a service by using datas sent in the body of the request",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Services successfully retrieved from the database",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Service already exist",
                            content = @Content(schema = @Schema())
                    )
            }
    )
    @PostMapping("/services")
    public ResponseEntity createService(@RequestBody @Valid ServiceRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }



    @GetMapping("/public/services/{id}/proposals")
    public ResponseEntity getProposalsByServiceId(@PathVariable int id){
        return ResponseEntity.ok(service.getProposalsByServiceId(id));
    }
}

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

@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api/service")
public class Service {

    @Autowired
    ServiceForServiceInterface service;


    @Operation(
            summary = "Return all services",
            security = { @SecurityRequirement(name= "bearerAuth")},
            description = "Retrieve all services from the database and return them to the user as a list of services that can be blank.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Services successfully retrieved from the database",
                            content = @Content()
                    )
            }
    )
    @GetMapping("")
    public ResponseEntity getAllServices(){
        return ResponseEntity.ok(service.getAll());
    }


    @Operation(
            summary = "retrieve service by his id",
            security = { @SecurityRequirement(name = "bearerAuth") },
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
    @GetMapping("/{id}")
    public ResponseEntity getServiceById(@PathVariable int id){
        ServiceResponseDTO dto=service.getById(id);

        if(dto==null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(dto);
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
    @PostMapping("")
    public ResponseEntity createService(@RequestBody @Valid ServiceRequestDTO dto){
        ServiceResponseDTO responseDTO=service.save(dto);

        Map<String,Object> response=new HashMap<>();

        if(responseDTO==null)
            return ResponseEntity.status(401).build();
        else
        {
            response.put("service",responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    }

    @Operation(
            summary = "Retrieve all Providers providing an specific service",
            description = "This endpoint allow to retrieve all providers providing an specific service by using the id of the service",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "No service owning this id"
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "retrieved all providers in a list even if there is no provider, you gonna get a blank list"
                    )
            }
    )
    @GetMapping("/{serviceId}/providers")
    public ResponseEntity getProviderByServiceId(@PathVariable int serviceId){
        return ResponseEntity.ok(service.getProvidersByServiceId(serviceId));
    }
}

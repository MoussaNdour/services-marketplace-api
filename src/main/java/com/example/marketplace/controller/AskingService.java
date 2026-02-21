package com.example.marketplace.controller;

import com.example.marketplace.dto.request.AskingServiceRequestDTO;
import com.example.marketplace.service.interfaces.ServiceAsking_ServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api")
public class AskingService {

    @Autowired
    ServiceAsking_ServiceInterface service;

    @Operation(
            summary = "Getting all asking services",
            description = "",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all asking services"
                    )
            }
    )
    @GetMapping("/public/asking-service")
    public ResponseEntity getAllserviceAsking(){
        return ResponseEntity.ok(service.getAll());
    }


    @Operation(
            summary = "Getting an asking service by using his id",
            description = "",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "successfully retrieved the asking service"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No asking service found with this id"
                    )
            }
    )
    @GetMapping("/public/asking-service/{id}")
    public ResponseEntity getServiceAskingById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }



    @Operation(
            summary = "Create an service asking",
            description = "",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully created the asking service"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No client or no service proposal found with this id"
                    )
            }
    )
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/asking-service")
    public ResponseEntity createServiceAsking(@RequestBody @Valid AskingServiceRequestDTO asking){
        return ResponseEntity.status(201).body(service.save(asking));
    }

    @Operation(
            summary = "Delete an service asking",
            description = "Delete an service asking by using his id",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "No Asking Service Found with this id"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "AskingService successfully deleted"
                    )
            }
    )
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/public/asking-service/{id}")
    public ResponseEntity deleteServiceAsking(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

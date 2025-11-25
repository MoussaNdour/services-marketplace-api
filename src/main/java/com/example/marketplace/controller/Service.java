package com.example.marketplace.controller;


import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api/service")
public class Service {

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
    @GetMapping("")
    public ResponseEntity getAllServices(){
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

        if(responseDTO==null)
            return ResponseEntity.status(401).build();
        else
            return ResponseEntity.ok().build();
    }
}

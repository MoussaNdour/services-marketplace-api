package com.example.marketplace.controller;

import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.User;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Provider {

    @Autowired
    ProviderServiceInterface service;

    @Operation(
            summary = "",
            description = "",
            responses = {
                    @ApiResponse(
                            description = "",
                            responseCode = ""
                    )

            }
    )
    @GetMapping("/public/providers")
    public ResponseEntity getAllProviders(){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(
            summary = "",
            description = "",
            responses = {
                    @ApiResponse(
                            description = "",
                            responseCode = ""
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = ""
                    )
            }
    )
    @GetMapping("/public/provider/{idprovider}")
    public ResponseEntity getProviderById(@PathVariable int idprovider){

        return ResponseEntity.ok(service.getById(idprovider));
    }

    @Operation(
            summary = "getting all services proposed by a provider",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "getting all services proposed by a provider"
    )
    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/provider/{email}/services")
    public ResponseEntity<List<ServiceResponseDTO>> getServicesByProvider(@PathVariable String email,@AuthenticationPrincipal User user){
        return ResponseEntity.ok(service.getAllServicesByProvider(user,email));
    }

}

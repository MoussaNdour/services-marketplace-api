package com.example.marketplace.controller;


import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.entity.User;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:8000")
@RestController
@RequestMapping("/api/service-proposal")
public class ServiceProposal {

    @Autowired
    ServiceProposalServiceInterface service;

    @PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "",
            responses = {
                    @ApiResponse(
                            responseCode = "",
                            description = ""
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity proposeService(@Valid @RequestBody ServiceProposalRequestDTO dto, @AuthenticationPrincipal User user){
        service.saveServiceProposal(dto,user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Service proposal saved");
    }



    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("")
    public ResponseEntity getServicesProposals(){

        return ResponseEntity.ok(service.getAll());
    }




    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth")},
            description = "",
            responses = {

            }
    )
    @GetMapping("/{idserviceproposal}")
    public ResponseEntity getServiceProposal(@PathVariable int idserviceproposal)
    {
        return ResponseEntity.ok(service.getById(idserviceproposal));
    }


    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "",
            responses = {

            }
    )
    @PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
    @DeleteMapping("/{idserviceproposal}")
    public ResponseEntity deleteServiceProposal(@PathVariable int idserviceproposal){
        service.deleteById(idserviceproposal);

        return ResponseEntity.ok().build();
    }

}

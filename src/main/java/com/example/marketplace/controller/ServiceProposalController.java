package com.example.marketplace.controller;


import com.example.marketplace.assembler.ServiceProposalAssembler;
import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;

import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ServiceProposalController {

    private final ServiceProposalServiceInterface service;
    private final ServiceProposalAssembler assembler;


    public ServiceProposalController(ServiceProposalServiceInterface service, ServiceProposalAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

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
    @PostMapping("/service-proposals")
    public ResponseEntity proposeService(@Valid @RequestBody ServiceProposalRequestDTO dto){
        service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Service proposal saved");
    }



    @Operation(
            summary = "",
            security = {  }
    )
    @GetMapping("/public/service-proposals")
    public ResponseEntity getServiceProposals(){
        return ResponseEntity.ok(assembler.toCollectionModel(service.getAll()));
    }



    @Operation(
            summary = "",
            security = { },
            description = "",
            responses = {

            }
    )
    @GetMapping("/public/service-proposal/{id}")
    public ResponseEntity<EntityModel<ServiceProposalResponseDTO>> getServiceProposalById(@PathVariable int id)
    {
        return ResponseEntity.ok(assembler.toModel(service.getById(id)));
    }


    @GetMapping("/public/service-proposal/{id}/provider")
    public ResponseEntity getProviderByProposalId(@PathVariable int id) {
        return ResponseEntity.ok(service.getProviderOfProposal(id));
    }

    @GetMapping("/public/service-proposal/{id}/service")
    public ResponseEntity getServiceByProposalId(@PathVariable int id) {
        return ResponseEntity.ok(service.getServiceOfProposal(id));
    }
    
    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "",
            responses = {

            }
    )
    @PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
    @DeleteMapping("/service-proposal/{id}")
     public ResponseEntity deleteServiceProposal(@PathVariable int id){
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

package com.example.marketplace.controller;


import com.example.marketplace.assembler.ServiceAssembler;
import com.example.marketplace.assembler.ServiceProposalAssembler;
import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.request.ServiceProposalUpdateDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;

import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ServiceProposalController {

    private final ServiceProposalServiceInterface service;

    private final ServiceProposalAssembler assembler;

    private final ServiceAssembler serviceAssembler;


    public ServiceProposalController(ServiceProposalServiceInterface service, ServiceProposalAssembler assembler, ServiceAssembler serviceAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.serviceAssembler = serviceAssembler;
    }

    @PreAuthorize("hasRole('PROVIDER')")
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
    @GetMapping("/public/service-proposals/{id}")
    public ResponseEntity<EntityModel<ServiceProposalResponseDTO>> getServiceProposalById(@PathVariable int id)
    {
        return ResponseEntity.ok(assembler.toModel(service.getById(id)));
    }


    @GetMapping("/public/service-proposals/{id}/provider")
    public ResponseEntity getProviderByProposalId(@PathVariable int id) {
        return ResponseEntity.ok(service.getProviderOfProposal(id));
    }

    @GetMapping("/public/service-proposals/{id}/service")
    public ResponseEntity getServiceByProposalId(@PathVariable int id) {
        return ResponseEntity.ok(serviceAssembler.toModel(service.getServiceOfProposal(id)));
    }
    
    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "",
            responses = {

            }
    )
    @PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
    @DeleteMapping("/service-proposals/{id}")
     public ResponseEntity deleteServiceProposal(@PathVariable int id){
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PutMapping("/service-proposals/{id}")
    public ResponseEntity updateProposal(@PathVariable int id, @RequestBody @Valid ServiceProposalUpdateDTO update)
    {
        return ResponseEntity.ok(service.updateService(id,update));
    }

}

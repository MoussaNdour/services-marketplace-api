package com.example.marketplace.controller;

import com.example.marketplace.assembler.AskingServiceAssembler;
import com.example.marketplace.assembler.ServiceProposalAssembler;
import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.entity.User;
import com.example.marketplace.service.interfaces.AskingInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class AskingServiceController {

    private final AskingInterface service;

    private final AskingServiceAssembler assembler;

    private final ServiceProposalAssembler proposalAssembler;


    public AskingServiceController(AskingInterface service, AskingServiceAssembler assembler, ServiceProposalAssembler proposalAssembler)
    {
        this.service=service;
        this.assembler = assembler;
        this.proposalAssembler = proposalAssembler;
    }

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
    @GetMapping("/public/askingservices")
    public ResponseEntity getAllserviceAsking(){
        return ResponseEntity.ok(assembler.toCollectionModel(service.getAll()));
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
    @GetMapping("/public/askingservices/{id}")
    public ResponseEntity getServiceAskingById(@PathVariable int id){
        return ResponseEntity.ok(assembler.toModel(service.getById(id)));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/askingservices")
    public ResponseEntity getMyAskings(@AuthenticationPrincipal User user)
    {
        return ResponseEntity.ok(assembler.toCollectionModel(service.getClientAskings(user)));
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
    @PostMapping("/askingservices")
    public ResponseEntity createServiceAsking(@RequestBody @Valid AskingRequestDTO asking){
        return ResponseEntity.status(201).body(assembler.toModel(service.save(asking)));
    }

    @Operation(
            summary = ""
    )
    @GetMapping("public/askingservices/{id}/client")
    public ResponseEntity getAskingClient(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getClientByAskingId(id));
    }

    @Operation(
            summary = ""
    )
    @GetMapping("public/askingservices/{id}/proposal")
    public ResponseEntity getAskingPropoSal(@PathVariable int id)
    {
        return ResponseEntity.ok(proposalAssembler.toModel(service.getProposalByAskingId(id)));
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
    @DeleteMapping("/askingservices/{id}")
    public ResponseEntity deleteServiceAsking(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

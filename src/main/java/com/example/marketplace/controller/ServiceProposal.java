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


@RestController
@RequestMapping("/api")
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
        return ResponseEntity.ok(service.getAll());
    }




    @Operation(
            summary = "",
            security = { },
            description = "",
            responses = {

            }
    )
    @GetMapping("/public/service-proposal/{idserviceproposal}")
    public ResponseEntity getServiceProposalById(@PathVariable int idserviceproposal)
    {
        return ResponseEntity.ok(service.getById(idserviceproposal));
    }


//    @Operation(
//            summary = "",
//            security = {  },
//            description = "",
//            responses = {
//                    @ApiResponse(
//                            description = "",
//                            responseCode = ""
//                    )
//            }
//    )
//    @GetMapping("/service-proposal/{serviceid}/{providerid}")
//    public ResponseEntity getServiceProposalByServiceIdAndProviderId(@PathVariable int serviceid, @PathVariable int providerid){
//        return ResponseEntity.ok(service.getServiceProposalByServiceIdAndProviderId(serviceid,providerid));
//    }


    @Operation(
            summary = "",
            security = { @SecurityRequirement(name = "bearerAuth") },
            description = "",
            responses = {

            }
    )
    @PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
    @DeleteMapping("/service-proposal/{idserviceproposal}")
    public ResponseEntity deleteServiceProposal(@PathVariable int idserviceproposal){
        service.deleteById(idserviceproposal);

        return ResponseEntity.ok().build();
    }

}

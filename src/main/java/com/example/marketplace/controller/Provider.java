package com.example.marketplace.controller;

import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provider")
@CrossOrigin(origins="http://localhost:8000",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS})
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
    @GetMapping("")
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
    @GetMapping("/{idprovider}")
    public ResponseEntity getProviderById(@PathVariable int idprovider){

        return ResponseEntity.ok(service.getById(idprovider));
    }

}

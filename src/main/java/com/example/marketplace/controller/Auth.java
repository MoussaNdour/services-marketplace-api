package com.example.marketplace.controller;


import com.example.marketplace.dto.request.*;
import com.example.marketplace.entity.User;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/public/auth")
public class Auth {


    @Autowired
    AuthServiceInterface service;

    @Operation(
            summary = "Authenticate a user",
            description = "Checks the email & password and returns a jwt and the infos on the connected user in an object called user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully authenticated",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid email or password"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "User role not supported or Unauthorized"
                    )
            }
    )
    @PostMapping("/connect")
    public ResponseEntity connection(@RequestBody @Valid Login user){
        return ResponseEntity.ok(service.connect(user));
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new account if the email is not already taken. The user must be a client, a provider or an admin",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "409", description = "Account already exist for this email"),
                    @ApiResponse(responseCode = "403", description = "User role Unauthorized")
            }
    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegistrationRequest request){
        return ResponseEntity.ok(service.registerUser(request));
    }


    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody @Valid Payload payload){
        return ResponseEntity.ok(service.refreshToken(payload));
    }

}

package com.example.marketplace.controller;


import com.example.marketplace.dto.request.Login;
import com.example.marketplace.dto.request.UserRequest;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@CrossOrigin(origins="http://localhost:8000")
@RequestMapping("api/auth")
public class Auth {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthServiceInterface service;

    @Operation(
            summary = "Authenticate a user",
            description = "Checks the email & password and returns authentication information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully authenticated",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid email or password"
                    )
            }
    )
    @PostMapping("/connect")
    public ResponseEntity connection(@RequestBody @Valid Login user){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

            // Si on arrive ici, l'authentification est valide
            UserDetails user1 = (UserDetails) authentication.getPrincipal();

            // Tu peux retourner un JWT, ou des infos basiques
            return ResponseEntity.ok("Authentificated : " + user1.getUsername());

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email or password incorect");
        }

    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new account if the email is not already taken",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "409", description = "Email already exists")
            }
    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRequest user){
        if(service.checkUserEmail(user.getEmail())) {
            service.registerUser(user);

            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There already exist a user with this email");
    }
}

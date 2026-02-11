package com.example.marketplace.controller;


import com.example.marketplace.dto.request.*;
import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.entity.User;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.marketplace.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins="http://localhost:8000",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS})
@RequestMapping("api/auth")
public class Auth {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthServiceInterface service;

    @Autowired
    JwtService jwtService;

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
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

            // Si on arrive ici, l'authentification est valide
            User user1 = (User) authentication.getPrincipal();

            switch (user1.getRole())
            {
                case "ADMIN"->{

                    AdminResponseDTO admin=service.findAdmin(user1.getEmail());
                    if(admin!=null)
                    {
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("refreshToken",jwtService.generateRefreshToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("user",admin);

                        return ResponseEntity.ok(response);
                    }
                    else{
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));

                        return ResponseEntity.ok(response);
                    }
                }
                case "CLIENT"->{

                    ClientResponseDTO client=service.findClient(user1.getEmail());

                    if(client!=null){
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("refreshToken",jwtService.generateRefreshToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("user",client);

                        return ResponseEntity.ok(response);
                    }
                    else{
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));


                        return ResponseEntity.ok(response);
                    }
                }
                case "PROVIDER"->{

                    ProviderRespoonseDTO provider=service.findProvider(user1.getEmail());

                    if(provider!=null)
                    {
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("refreshToken",jwtService.generateRefreshToken(user1.getUsername(),user1.getRole().toUpperCase()));
                        response.put("user",provider);

                        return ResponseEntity.ok(response);
                    }
                    else{
                        Map<String,Object> response=new HashMap<>();
                        response.put("token", jwtService.generateToken(user1.getUsername(),user1.getRole().toUpperCase()));

                        return ResponseEntity.ok(response);
                    }
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Rôle utilisateur non valide ou non supporté.");
                }
            }


        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email or password incorect");
        }
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
        
        switch (request.role.name()) {
            case "ADMIN" -> {
                AdminResponseDTO admin = service.registerAdmin((AdminRequestDTO) request);

                return ResponseEntity.ok(admin);

            }
            case "CLIENT" -> {
                ClientResponseDTO client = service.registerClient((ClientRequestDTO) request);

                return ResponseEntity.ok(client);

            }
            case "PROVIDER" -> {
                ProviderRespoonseDTO provider = service.registerProvider((ProviderRequestDTO) request);

                return ResponseEntity.ok(provider);

            }
            default -> {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unknown role");
            }
        }

    }

    @Operation(
        security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@AuthenticationPrincipal User user){

        return ResponseEntity.ok(service.refreshToken(user));
    }

    @Operation(
            summary = "Get All existing users",
            description = "Only for administrators",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }
}

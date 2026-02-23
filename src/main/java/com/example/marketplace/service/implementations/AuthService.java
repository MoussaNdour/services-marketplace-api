package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.*;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.EmailAlreadyUserException;
import com.example.marketplace.exception.UnauthorizedUserRoleException;
import com.example.marketplace.mapper.request.ClientRequestMapper;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.response.AdminResponseMapper;
import com.example.marketplace.mapper.response.ClientResponseMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.repository.AdminRepository;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.JwtService;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.marketplace.dto.response.*;


@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminResponseMapper adminResponseMapper;

    @Autowired
    ClientRequestMapper clientRequestMapper;

    @Autowired
    ClientResponseMapper clientResponseMapper;

    @Autowired
    ProviderRequestMapper providerRequestMapper;

    @Autowired
    ProviderResponseMapper providerResponseMapper;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public boolean isEmailFree(String email) {
        User user=userRepository.findByEmail(email).orElse(null);

        if(user==null)
            return true;
        else
            return false;
    }

    @Transactional
    @Override
    public ClientResponseDTO registerClient(ClientRequestDTO client) {
        if(!isEmailFree(client.getEmail()))
            throw new EmailAlreadyUserException("There is already an account with this email.");
        else{
            User user=new User();
            user.setRole("CLIENT");
            user.setEmail(client.getEmail());
            user.setPassword(encoder.encode(client.getPassword()));

            userRepository.save(user);

            Client clientEntity=clientRequestMapper.toEntity(client);
            clientEntity.setUser(user);

            Client savedEntity=clientRepository.save(clientEntity);

            return clientResponseMapper.toDTO(savedEntity);
        }
    }

    @Transactional
    @Override
    public ProviderResponseDTO registerProvider(ProviderRequestDTO provider) {
        if(!isEmailFree((provider.getEmail())))
            throw new EmailAlreadyUserException("There is already an account with this email.");
        else{
            User user=new User();
            user.setRole("PROVIDER");
            user.setEmail(provider.getEmail());
            user.setPassword(encoder.encode(provider.getPassword()));

            userRepository.save(user);


            Provider providerEntity=providerRequestMapper.toEntity(provider);
            providerEntity.setUser(user);

            if(providerEntity.getYearsOfExperience()<3)
                providerEntity.setLevel("JUNIOR");
            else if(providerEntity.getYearsOfExperience()<5)
            {
                providerEntity.setLevel("MEDIUM");
            }
            else{
                providerEntity.setLevel("SENIOR");
            }

            Provider savedEntiy=providerRepository.save(providerEntity);

            return providerResponseMapper.toDTO(savedEntiy);
        }
    }


    @Override
    public LoginResponseDTO connect(Login login) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );

        User user = (User) auth.getPrincipal();
        String role = user.getRole().toUpperCase();


        Object profile = findProfileByRole(user.getEmail(), role);

        if (profile == null) {
            throw new IllegalStateException("User authenticated but no profile found for role: " + role);
        }

        return LoginResponseDTO.builder()
                .token(jwtService.generateToken(user.getUsername(), role))
                .refreshToken(jwtService.generateRefreshToken(user.getUsername(), role))
                .profile(profile)
                .build();
    }


    private Object findProfileByRole(String email, String role) {
        return switch (role) {
            case "ADMIN" -> adminResponseMapper.toDTO(adminRepository.findByUserEmail(email).orElse(null));
            case "CLIENT" -> clientResponseMapper.toDTO(clientRepository.findByUserEmail(email).orElse(null));
            case "PROVIDER" -> providerResponseMapper.toDTO(providerRepository.findByUserEmail(email).orElse(null));
            default -> null;
        };
    }


    @Override
    public String refreshToken(User user) {
        return jwtService.generateToken(user.getEmail(),user.getRole());
    }

    @Override
    public Object registerUser(RegistrationRequest request) {
        switch (request.role.name()) {

            case "CLIENT" -> {
                return registerClient((ClientRequestDTO) request);
            }
            case "PROVIDER" -> {
                return registerProvider((ProviderRequestDTO) request);
            }
            default -> {
                throw new UnauthorizedUserRoleException("This role is not authorized");
            }
        }
    }

}

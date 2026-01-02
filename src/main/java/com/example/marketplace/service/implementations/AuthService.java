package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.AdminRequestDTO;
import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.dto.response.UserResponseDTO;
import com.example.marketplace.entity.Admin;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.EmailAlreadyUserException;
import com.example.marketplace.mapper.request.AdminRequestMapper;
import com.example.marketplace.mapper.request.ClientRequestMapper;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.response.AdminResponseMapper;
import com.example.marketplace.mapper.response.ClientResponseMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.mapper.response.UserResponseMapper;
import com.example.marketplace.repository.AdminRepository;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.interfaces.AdminServiceInterface;
import com.example.marketplace.service.interfaces.AuthServiceInterface;
import com.example.marketplace.service.interfaces.ClientServiceInterface;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    ClientRequestMapper clientRequestMapper;

    @Autowired
    ClientResponseMapper clientResponseMapper;

    @Autowired
    ProviderRequestMapper providerRequestMapper;

    @Autowired
    ProviderResponseMapper providerResponseMapper;

    @Autowired
    AdminRequestMapper adminRequestMapper;

    @Autowired
    AdminResponseMapper adminResponseMapper;


    @Autowired
    PasswordEncoder encoder;


    @Autowired
    ClientServiceInterface clientService;


    @Autowired
    ProviderServiceInterface providerService;

    @Autowired
    AdminServiceInterface adminService;

    @Autowired
    UserResponseMapper userResponseMapper;

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
            try {
                userRepository.save(user);
            }
            catch(DataIntegrityViolationException e)
            {
                return null;
            }

            Client clientEntity=clientRequestMapper.toEntity(client);
            clientEntity.setUser(user);

            Client savedEntity=clientRepository.save(clientEntity);

            return clientResponseMapper.toDTO(savedEntity);
        }
    }

    @Transactional
    @Override
    public ProviderRespoonseDTO registerProvider(ProviderRequestDTO provider) {
        if(!isEmailFree((provider.getEmail())))
            throw new EmailAlreadyUserException("There is already an account with this email.");
        else{
            User user=new User();
            user.setRole("PROVIDER");
            user.setEmail(provider.getEmail());
            user.setPassword(encoder.encode(provider.getPassword()));
            try {
                userRepository.save(user);
            }
            catch(DataIntegrityViolationException e)
            {
                return null;
            }

            Provider providerEntity=providerRequestMapper.toEntity(provider);
            providerEntity.setUser(user);

            Provider savedEntiy=providerRepository.save(providerEntity);

            return providerResponseMapper.toDTO(savedEntiy);
        }
    }

    @Transactional
    @Override
    public AdminResponseDTO registerAdmin(AdminRequestDTO admin) {
        if(!isEmailFree(admin.getEmail()))
            throw new EmailAlreadyUserException("There is already an account with this email.");
        else{
            User user=new User();
            user.setRole("ADMIN");
            user.setEmail(admin.getEmail());
            user.setPassword(encoder.encode(admin.getPassword()));
            try {
                userRepository.save(user);
            }
            catch(DataIntegrityViolationException e)
            {
                return null;
            }

            Admin adminEntiy=adminRequestMapper.toEntity(admin);
            adminEntiy.setUser(user);

            Admin savedEntiy=adminRepository.save(adminEntiy);

            return adminResponseMapper.toDTO(savedEntiy);
        }
    }

    @Override
    public ClientResponseDTO findClient(String email) {
        return clientService.getByEmail(email);
    }

    @Override
    public ProviderRespoonseDTO findProvider(String email) {
        return providerService.getByEmail(email);
    }

    @Override
    public AdminResponseDTO findAdmin(String email) {
        return adminService.getByEmail(email);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(){
        List<UserResponseDTO> users=new ArrayList<>();

        for(User user:userRepository.findAll()){
            users.add(userResponseMapper.toDTO(user));
        }

        return users;
    }

}

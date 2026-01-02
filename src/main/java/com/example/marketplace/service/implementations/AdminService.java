package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.AdminRequestDTO;
import com.example.marketplace.dto.response.AdminResponseDTO;
import com.example.marketplace.entity.Admin;
import com.example.marketplace.mapper.request.AdminRequestMapper;
import com.example.marketplace.mapper.response.AdminResponseMapper;
import com.example.marketplace.repository.AdminRepository;
import com.example.marketplace.service.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements AdminServiceInterface {

    @Autowired
    AdminRepository repository;

    @Autowired
    AdminResponseMapper responseMapper;

    @Autowired
    AdminRequestMapper requestMapper;

    @Override
    public AdminResponseDTO save(AdminRequestDTO dto) {
        return null;
    }

    @Override
    public List<AdminResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public AdminResponseDTO getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void disableAccount(String email) {

    }

    @Override
    public AdminResponseDTO getByEmail(String email) {
        Admin admin=repository.findByUserEmail(email).orElse(null);

        if(admin==null)
            return null;
        else
            return responseMapper.toDTO(admin);
    }
}

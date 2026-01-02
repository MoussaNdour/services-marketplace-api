package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ClientRequestDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.entity.Client;
import com.example.marketplace.mapper.request.ClientRequestMapper;
import com.example.marketplace.mapper.response.ClientResponseMapper;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.service.interfaces.ClientServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements ClientServiceInterface {

    @Autowired
    ClientRequestMapper requestMapper;

    @Autowired
    ClientResponseMapper responseMapper;

    @Autowired
    ClientRepository repository;


    @Override
    public ClientResponseDTO save(ClientRequestDTO dto) {
        return null;
    }

    @Override
    public List<ClientResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public ClientResponseDTO getById(int id) {
        return null;
    }


    @Override
    public void deleteById(int id) {

    }

    @Override
    public void disableAccount(String email) {

    }


    @Override
    public ClientResponseDTO getByEmail(String email) {
        Client client=repository.findByUserEmail(email).orElse(null);

        if(client==null)
            return null;
        else
            return responseMapper.toDTO(client);
    }


}

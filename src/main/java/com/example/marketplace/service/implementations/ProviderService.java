package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService implements ProviderServiceInterface {

    @Autowired
    ProviderRepository repository;

    @Autowired
    ProviderRequestMapper requestMapper;

    @Autowired
    ProviderResponseMapper responseMapper;

    @Override
    public ProviderRespoonseDTO save(ProviderRequestDTO dto) {
        return null;
    }

    @Override
    public List<ProviderRespoonseDTO> getAll() {
        return List.of();
    }

    @Override
    public ProviderRespoonseDTO getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void disableAccount(String email) {

    }

    @Override
    public ProviderRespoonseDTO getByEmail(String email) {
        Provider provider=repository.findByUserEmail(email).orElse(null);

        if(provider==null)
            return null;
        else
            return responseMapper.toDTO(provider);
    }
}

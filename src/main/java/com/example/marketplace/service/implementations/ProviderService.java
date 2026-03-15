package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ProviderRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.ForbiddenOperationException;
import com.example.marketplace.exception.ProviderNotFoundException;
import com.example.marketplace.mapper.request.ProviderRequestMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService implements ProviderServiceInterface {

    @Autowired
    ProviderRepository repository;

    @Autowired
    ProviderRequestMapper requestMapper;

    @Autowired
    ProviderResponseMapper responseMapper;

    @Autowired
    ServiceForServiceInterface serviceForService;

    @Override
    public ProviderResponseDTO save(ProviderRequestDTO dto) {
        return null;
    }

    @Override
    public List<ProviderResponseDTO> getAll() {
        List<ProviderResponseDTO> providers=new ArrayList<>();

        for(Provider provider:repository.findAll()){

            providers.add(responseMapper.toDTO(provider));
        }

        return providers;
    }

    @Override
    public ProviderResponseDTO getById(int id) {

        Provider provider=repository.findById(id).orElseThrow(
                ()->new ProviderNotFoundException("Provider not found for this id")
        );

        return responseMapper.toDTO(provider);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void disableAccount(String email) {

    }

    @Override
    public ProviderResponseDTO getByEmail(String email) {
        Provider provider=repository.findByUserEmail(email).orElse(null);

        if(provider==null)
            return null;
        else
            return responseMapper.toDTO(provider);
    }



    @Override
    public List<ServiceResponseDTO> getAllServicesByProvider(User user,String email) {
        if(!email.equals(user.getEmail()))
            throw new ForbiddenOperationException("This user is not authorized to access to others users' datas");
        return serviceForService.getAllServicesByProvider(user.getEmail());
    }

}

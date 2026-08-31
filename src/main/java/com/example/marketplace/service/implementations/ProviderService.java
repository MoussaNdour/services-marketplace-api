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
import com.example.marketplace.mapper.response.ServiceResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService implements ProviderServiceInterface {


    private final ProviderRepository repos;

    private final ProviderResponseMapper responseMapper;

    private final ServiceRepository serviceRepos;

    private final ServiceResponseMapper serviceResponseMapper;

    public ProviderService(ProviderRepository repos,ProviderResponseMapper responseMapper,ServiceRepository serviceRepos,ServiceResponseMapper serviceResponseMapper){
        this.repos=repos;
        this.responseMapper=responseMapper;
        this.serviceRepos=serviceRepos;
        this.serviceResponseMapper=serviceResponseMapper;
    }


    @Override
    public ProviderResponseDTO save(ProviderRequestDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ProviderResponseDTO> getAll() {
        List<ProviderResponseDTO> providers=new ArrayList<>();

        for(Provider provider:repos.findAll()){

            providers.add(responseMapper.toDTO(provider));
        }

        return providers;
    }

    @Override
    public ProviderResponseDTO getById(int id) {

        Provider provider=repos.findById(id).orElseThrow(
                ()->new ProviderNotFoundException("Provider not found for this id")
        );

        return responseMapper.toDTO(provider);
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disableAccount(String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProviderResponseDTO getByEmail(String email) {
        Provider provider=repos.findByUserEmail(email).orElseThrow(
                () -> new ProviderNotFoundException("Provider not found for this email")
        );


        return responseMapper.toDTO(provider);
    }



    @Override
    public List<ServiceResponseDTO> getAllServicesByProvider(String email) {

        List<ServiceResponseDTO> services = new ArrayList<>();

        for(com.example.marketplace.entity.Service service: serviceRepos.getAllByProviderEmail(email))
        {
            services.add(serviceResponseMapper.toDTO(service));
        }

        return services;
    }

}

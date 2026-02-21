package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ProviderRespoonseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.exception.NonexistingImageException;
import com.example.marketplace.exception.ServiceNotFoundException;
import com.example.marketplace.mapper.request.ServiceRequestMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.mapper.response.ServiceResponseMapper;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceForService implements ServiceForServiceInterface {

    @Autowired
    ServiceRepository repos;

    @Autowired
    ServiceRequestMapper requestMapper;

    @Autowired
    ServiceResponseMapper responseMapper;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ServiceProposalServiceInterface serviceProposalService;

    @Autowired
    ProviderResponseMapper providerResponseMapper;

    @Override
    public ServiceResponseDTO save(ServiceRequestDTO dto) {

        imageRepository.findById(dto.getIdImage()).orElseThrow(()->new NonexistingImageException("Image not existing for the id " + dto.getIdImage()));


        com.example.marketplace.entity.Service service=requestMapper.toEntity(dto);
        service.setCreatedAt(new Date());
        service=repos.save(service);

        if(service==null)
            return null;
        else{
            return responseMapper.toDTO(service);
        }
    }

    @Override
    public List<ServiceResponseDTO> getAll() {
        List<ServiceResponseDTO> services=new ArrayList<>();

        for(com.example.marketplace.entity.Service service:repos.findAll()){
            services.add(responseMapper.toDTO(service));
        }

        return services;
    }

    @Override
    public ServiceResponseDTO getById(int id) {
        com.example.marketplace.entity.Service service=repos.findById(id).orElse(null);

        if(service==null)
            return null;
        else{
            return responseMapper.toDTO(service);
        }
    }

    @Override
    public void deleteById(int id) {
        repos.deleteById(id);
    }

    @Override
    public List<ProviderRespoonseDTO> getProvidersByServiceId(int serviceid) {
        if(getById(serviceid)==null)
            throw new ServiceNotFoundException("There is no service with this id");

        List<ProviderRespoonseDTO> providers=new ArrayList<>();

        for(Provider provider:serviceProposalService.getProvidersByServiceId(serviceid)){
            providers.add(providerResponseMapper.toDTO(provider));
        }

        return providers;
    }

    @Override
    public List<ServiceResponseDTO> searchService(String name) {

        List<ServiceResponseDTO> results=new ArrayList<>();

        for(com.example.marketplace.entity.Service service:repos.searchService(name)){
            results.add(responseMapper.toDTO(service));
        }

        return results;
    }
}

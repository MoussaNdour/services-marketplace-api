package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.mapper.request.ServiceRequestMapper;
import com.example.marketplace.mapper.response.ServiceResponseMapper;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceForService implements ServiceForServiceInterface {

    @Autowired
    ServiceRepository repos;

    @Autowired
    ServiceRequestMapper requestMapper;

    @Autowired
    ServiceResponseMapper responseMapper;

    @Override
    public ServiceResponseDTO save(ServiceRequestDTO dto) {
        com.example.marketplace.entity.Service service=repos.save(requestMapper.toEntity(dto));

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
}

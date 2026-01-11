package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.AskingServiceRequestDTO;
import com.example.marketplace.dto.response.AskingServiceResponseDTO;
import com.example.marketplace.entity.AskingService;
import com.example.marketplace.exception.AskingServiceNotFoundException;
import com.example.marketplace.mapper.request.AskingServiceRequestMapper;
import com.example.marketplace.mapper.response.AskingServiceResponseMapper;
import com.example.marketplace.repository.AskingServiceRepository;
import com.example.marketplace.service.interfaces.ClientServiceInterface;
import com.example.marketplace.service.interfaces.ServiceAsking_ServiceInterface;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceAsking_Service implements ServiceAsking_ServiceInterface {

    @Autowired
    AskingServiceRepository repository;

    @Autowired
    AskingServiceRequestMapper requestMapper;

    @Autowired
    AskingServiceResponseMapper responseMapper;

    @Autowired
    ClientServiceInterface clientservice;

    ServiceProposalServiceInterface serviceproposalservice;

    @Override
    public AskingServiceResponseDTO save(AskingServiceRequestDTO dto) {
        clientservice.getById(dto.getIdclient());

        serviceproposalservice.getById(dto.getIdserviceproposal());


        AskingServiceResponseDTO response=responseMapper.toDTO(repository.save(requestMapper.toEntity(dto)));

        return response;
    }

    @Override
    public List<AskingServiceResponseDTO> getAll() {
        List<AskingServiceResponseDTO> askings=new ArrayList<>();

        for(AskingService asking:repository.findAll()){
            askings.add(responseMapper.toDTO(asking));
        }

        return askings;
    }

    @Override
    public AskingServiceResponseDTO getById(int id) {
        AskingService askingService=repository.findById(id).orElseThrow(
                ()->new AskingServiceNotFoundException("Not service Asking found for this id")
        );

        return responseMapper.toDTO(askingService);
    }

    @Override
    public void deleteById(int id) {
        repository.findById(id).orElseThrow(
                ()->new AskingServiceNotFoundException("Not service Asking found for this id")
        );

        repository.deleteById(id);
    }
}

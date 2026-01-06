package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.Service;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.ForbiddenOperationException;
import com.example.marketplace.exception.ProviderNotFoundException;
import com.example.marketplace.exception.ServiceNotFoundException;
import com.example.marketplace.mapper.request.ServiceProposalRequestMapper;
import com.example.marketplace.mapper.response.ServiceProposalResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.ServiceProposalRepository;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceProposalService implements ServiceProposalServiceInterface {

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    ServiceProposalRepository repository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceProposalRequestMapper requestMapper;

    @Autowired
    ServiceProposalResponseMapper responseMapper;

    @Override
    public ServiceProposalResponseDTO save(ServiceProposalRequestDTO dto) {
        Provider provider=providerRepository.findById(dto.getIdprovider()).orElseThrow(
                ()-> new ProviderNotFoundException("Provider not found")
        );

        Service service=serviceRepository.findById(dto.getIdservice()).orElseThrow(
                ()-> new ServiceNotFoundException("Service not found")
        );


        ServiceProposal serviceProposal=repository.save(requestMapper.toEntity(dto));

        return responseMapper.toDTO(serviceProposal);
    }

    @Override
    public List<ServiceProposalResponseDTO> getAll() {
        List<ServiceProposalResponseDTO> serviceProposals=new ArrayList<>();

        for(ServiceProposal serviceProposal:repository.findAll())
        {
            serviceProposals.add(responseMapper.toDTO(serviceProposal));
        }

        return serviceProposals;
    }

    @Override
    public ServiceProposalResponseDTO getById(int id) {
        ServiceProposal serviceProposal=repository.findById(id).orElseThrow(
                ()-> new ServiceNotFoundException("Service not found")
        );

        return responseMapper.toDTO(serviceProposal);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public Provider checkProvider(String email) {
        return providerRepository.findByUserEmail(email).orElse(null);
    }

    @Override
    public void saveServiceProposal(ServiceProposalRequestDTO dto, User user) {
        System.out.println(user.getEmail());
        Provider provider=checkProvider(user.getEmail());


        if(provider==null)
        {
            throw new ProviderNotFoundException("Provider profile not existing or not completed");
        }
        else{
            if(dto.getIdprovider()!=provider.getId()){
                throw new ForbiddenOperationException("Forbidden Operation");
            }
            else{
                Service service=serviceRepository.findById(dto.getIdservice()).orElseThrow(
                        ()->new ServiceNotFoundException("Requested Service has not been founded")
                );
                repository.save(requestMapper.toEntity(dto));
            }
        }
    }

    @Override
    public List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider) {
        List<ServiceProposalResponseDTO> serviceProposals=new ArrayList<>();

        for(ServiceProposal serviceProposal:repository.getServiceProposalsByIdProvider(idprovider))
        {
            serviceProposals.add(responseMapper.toDTO(serviceProposal));
        }

        return serviceProposals;
    }

    @Override
    public List<Provider> getProvidersByServiceId(int serviceid) {
        List<Provider> providers=new ArrayList<>();

        for(Provider provider:repository.getProvidersByServiceId(serviceid)){
            providers.add(provider);
        }

        return providers;
    }

}

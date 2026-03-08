package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.Service;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.*;
import com.example.marketplace.mapper.request.ServiceProposalRequestMapper;
import com.example.marketplace.mapper.response.ServiceProposalResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.ServiceProposalRepository;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ProviderServiceInterface;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceProposalService implements ServiceProposalServiceInterface {

    @Autowired
    ServiceProposalRepository repository;

    @Autowired
    ServiceProposalRequestMapper requestMapper;

    @Autowired
    ServiceProposalResponseMapper responseMapper;

    @Autowired
    ProviderRepository providerRepository;

    @Override
    public ServiceProposalResponseDTO save(ServiceProposalRequestDTO dto) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();

        if(user.getRole()!="PROVIDER")
            throw new UnauthorizedUserRoleException("User unauthorized to provide service");

        ServiceProposal proposal = requestMapper.toEntity(dto);

        proposal.setProvider(providerRepository.findByUserEmail(user.getEmail()).orElse(null));

        return responseMapper.toDTO(repository.save(proposal));
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
    public List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider) {
        List<ServiceProposalResponseDTO> serviceProposals=new ArrayList<>();

        for(ServiceProposal serviceProposal:repository.getServiceProposalsByIdProvider(idprovider))
        {
            serviceProposals.add(responseMapper.toDTO(serviceProposal));
        }

        return serviceProposals;
    }


    @Override
    public ServiceProposalResponseDTO getServiceProposalByServiceIdAndProviderId(int serviceid, int providerid) {
        ServiceProposal serviceProposal=repository.getServiceProposalByServiceIdAndProviderId(serviceid,providerid).orElseThrow(
                ()->new ServiceProposalNotFoundException("Service proposal not found")
        );

        return responseMapper.toDTO(serviceProposal);
    }

    @Override
    public List<ServiceProposalResponseDTO> getServiceProposalsByServiceId(int serviceId) {
        List<ServiceProposalResponseDTO> proposals=new ArrayList<>();

        for(ServiceProposal proposal:repository.findByServiceId(serviceId)){
            proposals.add(responseMapper.toDTO(proposal));
        }

        return proposals;
    }

}

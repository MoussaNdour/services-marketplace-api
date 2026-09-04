package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceProposalRequestDTO;
import com.example.marketplace.dto.response.ProviderResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Provider;
import com.example.marketplace.entity.Service;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.*;
import com.example.marketplace.mapper.request.ServiceProposalRequestMapper;
import com.example.marketplace.mapper.response.ProviderResponseMapper;
import com.example.marketplace.mapper.response.ServiceProposalResponseMapper;
import com.example.marketplace.mapper.response.ServiceResponseMapper;
import com.example.marketplace.repository.ProviderRepository;
import com.example.marketplace.repository.ServiceProposalRepository;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.marketplace.repository.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
public class ServiceProposalService implements ServiceProposalServiceInterface {


    private final ServiceProposalRepository repos;

    private final ServiceProposalRequestMapper requestMapper;

    private final ServiceProposalResponseMapper responseMapper;

    private final ProviderRepository providerRepository;

    private final ProviderResponseMapper providerResponseMapper;

    private final ServiceRepository serviceRepos;

    private final ServiceResponseMapper serviceResponseMapper;

    public ServiceProposalService(
            ServiceProposalRepository repos,
            ServiceProposalRequestMapper requestMapper,
            ServiceProposalResponseMapper responseMapper,
            ProviderRepository providerRepository,
            ProviderResponseMapper providerResponseMapper,
            ServiceRepository serviceRepos,
            ServiceResponseMapper serviceResponseMapper
            )
    {
        this.repos=repos;
        this.requestMapper=requestMapper;
        this.responseMapper=responseMapper;
        this.providerRepository=providerRepository;
        this.providerResponseMapper=providerResponseMapper;
        this.serviceRepos=serviceRepos;
        this.serviceResponseMapper=serviceResponseMapper;
    }

    @Override
    public ServiceProposalResponseDTO save(ServiceProposalRequestDTO dto) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();


        Service service = serviceRepos.findById(dto.getIdservice()).orElseThrow(()->
            new ServiceNotFoundException("Service not found for this id")
        );


        ServiceProposal proposal = requestMapper.toEntity(dto);
        Provider provider = providerRepository.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new ProviderNotFoundException("Aucun profil prestataire pour cet utilisateur"));
        proposal.setProvider(provider);

        proposal.setProvider(provider);
        proposal.setService(service);


        return responseMapper.toDTO(repos.save(proposal));
    }

    @Override
    public List<ServiceProposalResponseDTO> getAll() {
        List<ServiceProposalResponseDTO> serviceProposals=new ArrayList<>();

        for(ServiceProposal serviceProposal:repos.findAll())
        {
            serviceProposals.add(responseMapper.toDTO(serviceProposal));
        }

        return serviceProposals;
    }

    @Override
    public ServiceProposalResponseDTO getById(int id) {

        ServiceProposal serviceProposal=repos.findById(id).orElseThrow(
                ()-> new ServiceNotFoundException("Proposal not found")
        );

        return responseMapper.toDTO(serviceProposal);
    }

    @Override
    public void deleteById(int id) {
        repos.deleteById(id);
    }



    @Override
    public List<ServiceProposalResponseDTO> getServicesProposalByIdProvider(int idprovider) {
        List<ServiceProposalResponseDTO> serviceProposals=new ArrayList<>();

        for(ServiceProposal serviceProposal:repos.findByProviderId(idprovider))
        {
            serviceProposals.add(responseMapper.toDTO(serviceProposal));
        }

        return serviceProposals;
    }




    @Override
    public ProviderResponseDTO getProviderOfProposal(int id) {
        Optional<Provider> provider = providerRepository.getByProposalId(id);

        if(provider.isPresent())
            return providerResponseMapper.toDTO(provider.get());
        else
            throw new ProviderNotFoundException("Provider not found for this proposal");

    }

    @Override
    public ServiceResponseDTO getServiceOfProposal(int id) {
        Optional<Service> service = serviceRepos.getByProposalId(id);

        if(service.isPresent())
            return serviceResponseMapper.toDTO(service.get());
        else
            throw new ServiceNotFoundException("Service not found for this proposal");

    }

}

package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.dto.response.AskingResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.Asking;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.AskingServiceNotFoundException;
import com.example.marketplace.mapper.request.AskingServiceRequestMapper;
import com.example.marketplace.mapper.response.AskingServiceResponseMapper;
import com.example.marketplace.mapper.response.ClientResponseMapper;
import com.example.marketplace.mapper.response.ServiceProposalResponseMapper;
import com.example.marketplace.repository.AskingRepository;
import com.example.marketplace.repository.ClientRepository;
import com.example.marketplace.repository.ServiceProposalRepository;
import com.example.marketplace.service.interfaces.ClientServiceInterface;
import com.example.marketplace.service.interfaces.AskingInterface;
import com.example.marketplace.service.interfaces.ServiceProposalServiceInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AskingService implements AskingInterface {


    private final AskingRepository repository;


    private final AskingServiceRequestMapper requestMapper;


    private final AskingServiceResponseMapper responseMapper;


    private final ClientServiceInterface clientservice;

    private final ServiceProposalServiceInterface serviceproposalservice;

    private final ClientRepository clientRepos;

    private final ServiceProposalRepository proposalRepos;

    private final ClientResponseMapper clientResponseMapper;

    private final ServiceProposalResponseMapper proposalResponseMapper;

    public AskingService(AskingRepository repository, AskingServiceRequestMapper requestMapper, AskingServiceResponseMapper responseMapper, ClientServiceInterface clientservice, ServiceProposalServiceInterface serviceproposalservice, ClientRepository clientRepos, ServiceProposalRepository proposalRepos, ClientResponseMapper clientResponseMapper, ServiceProposalResponseMapper proposalResponseMapper) {
        this.repository = repository;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.clientservice = clientservice;
        this.serviceproposalservice = serviceproposalservice;
        this.clientRepos = clientRepos;
        this.proposalRepos = proposalRepos;
        this.clientResponseMapper = clientResponseMapper;
        this.proposalResponseMapper = proposalResponseMapper;
    }

    @Override
    public AskingResponseDTO save(AskingRequestDTO dto) {
        clientservice.getById(dto.getIdclient());

        serviceproposalservice.getById(dto.getIdserviceproposal());

        AskingResponseDTO response=responseMapper.toDTO(repository.save(requestMapper.toEntity(dto)));

        return response;
    }

    @Override
    public List<AskingResponseDTO> getAll() {
        List<AskingResponseDTO> askings=new ArrayList<>();

        for(Asking asking:repository.findAll()){
            askings.add(responseMapper.toDTO(asking));
        }

        return askings;
    }

    @Override
    public AskingResponseDTO getById(int id) {
        Asking askingService=repository.findById(id).orElseThrow(
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

    @Override
    public List<AskingResponseDTO> getClientAskings(User user) {
        List<AskingResponseDTO> askings = new ArrayList<>();

        for(Asking asking: repository.findByClientUserEmail(user.getEmail()))
        {
            askings.add(responseMapper.toDTO(asking));
        }

        return askings;
    }

    @Override
    public ClientResponseDTO getClientByAskingId(int id) {
        getById(id);

        Optional<Client> client = clientRepos.findByAskingId(id);

        if(client.isPresent())
            return clientResponseMapper.toDTO(client.get());
        else
            throw new IllegalStateException("Asking found but client not found.");

    }

    @Override
    public ServiceProposalResponseDTO getProposalByAskingId(int id) {
        getById(id);

        Optional<ServiceProposal> proposal = proposalRepos.findProposalByAskingId(id);

        if(proposal.isPresent())
            return proposalResponseMapper.toDTO(proposal.get());
        else
            throw new IllegalStateException("Asking found but no proposal was found");
    }
}

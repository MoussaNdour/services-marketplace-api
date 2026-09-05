package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.dto.request.AskingUpdateDTO;
import com.example.marketplace.dto.response.AskingResponseDTO;
import com.example.marketplace.dto.response.ClientResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.entity.Asking;
import com.example.marketplace.entity.Client;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.entity.User;
import com.example.marketplace.exception.AskingServiceNotFoundException;
import com.example.marketplace.exception.ClientNotFoundException;
import com.example.marketplace.exception.DuplicateAskingException;
import com.example.marketplace.exception.ServiceProposalNotFoundException;
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
import com.example.marketplace.type.AskingStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Asking> askings = repository.findByClientUserEmail(user.getEmail());

        for(Asking asking:askings){
            if(asking.getProposal().getId()==dto.getIdserviceproposal())
                throw new DuplicateAskingException("The client is already asking for this service");
        }


        Client client = clientRepos.findByUserEmail(user.getEmail()).orElseThrow(
                ()->new ClientNotFoundException("No profile found for this client")
        );

        ServiceProposal proposal = proposalRepos.findById(dto.getIdserviceproposal()).orElseThrow(
                ()-> new ServiceProposalNotFoundException("Offre introuvable pour cet id")
        );

        Asking asking = requestMapper.toEntity(dto);
        asking.setClient(client);
        asking.setProposal(proposal);

        AskingResponseDTO response=responseMapper.toDTO(repository.save(asking));

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();

        Asking asking = repository.findById(id).orElseThrow(
                ()->new AskingServiceNotFoundException("Not service Asking found for this id")
        );

        if(user.getRole().equals("CLIENT"))
        {
            if(!asking.getClient().getUser().getEmail().equals(user.getEmail()))
                throw new AccessDeniedException("This client is no allowed to deleted this service asking");
        }

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

    public AskingResponseDTO updateStatus(int id, AskingUpdateDTO dto) {
        Asking asking = repository.findById(id)
                .orElseThrow(() -> new AskingServiceNotFoundException("Demande introuvable"));

        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clientEmail = asking.getClient().getUser().getEmail();
        String providerEmail = asking.getProposal().getProvider().getUser().getEmail();
        AskingStatus newStatus = AskingStatus.valueOf(dto.getStatus());

        boolean isClient = clientEmail.equals(currentUser.getEmail());
        boolean isProvider = providerEmail.equals(currentUser.getEmail());

        if (isClient && newStatus == AskingStatus.CANCELLED
                && Objects.equals(asking.getStatus(), AskingStatus.PENDING.toString())) {
            requestMapper.updateEntityFromDto(dto,asking);

            repository.save(asking);
        } else if (isProvider && isValidProviderTransition(asking.getStatus(), newStatus)) {
            requestMapper.updateEntityFromDto(dto,asking);

            repository.save(asking);
        } else {
            throw new AccessDeniedException("Transition de statut non autorisée");
        }

        asking.setStatus(newStatus.name());
        return responseMapper.toDTO(repository.save(asking));
    }

    private boolean isValidProviderTransition(String status, AskingStatus newStatus) {
        return (status.equals("PENDING") && newStatus == AskingStatus.ACCEPTED)
                || (status.equals("PENDING") && newStatus == AskingStatus.REJECTED)
                || (status.equals("ACCEPTED") && newStatus == AskingStatus.COMPLETED);
    }
}

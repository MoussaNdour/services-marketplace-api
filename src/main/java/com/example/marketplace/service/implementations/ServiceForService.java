package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.CategoryResponseDTO;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.entity.Category;
import com.example.marketplace.entity.Image;
import com.example.marketplace.entity.ServiceProposal;
import com.example.marketplace.exception.CategoryNotFoundException;
import com.example.marketplace.exception.ServiceAlreadyExistingException;
import com.example.marketplace.exception.ServiceNotFoundException;
import com.example.marketplace.mapper.request.ServiceRequestMapper;
import com.example.marketplace.mapper.response.CategoryResponseMapper;
import com.example.marketplace.mapper.response.ServiceProposalResponseMapper;
import com.example.marketplace.mapper.response.ServiceResponseMapper;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ServiceProposalRepository;
import com.example.marketplace.repository.ServiceRepository;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceForService implements ServiceForServiceInterface {

    private final ServiceRepository repos;

    private final ServiceRequestMapper requestMapper;

    private final ServiceResponseMapper responseMapper;

    private final ImageRepository imageRepository;

    private final ServiceProposalRepository proposalRepos;

    private final ServiceProposalResponseMapper proposalResponseMapper;

    private final CategoryRepository categoryRepos;

    private final CategoryResponseMapper categeoryResponseMapper;

    public ServiceForService(ServiceRepository repos, ServiceRequestMapper requestMapper, ServiceResponseMapper responseMapper, ImageRepository imageRepository, ServiceProposalRepository proposalRepos, ServiceProposalResponseMapper proposalResponseMapper, CategoryRepository categoryRepos, CategoryResponseMapper categeoryResponseMapper) {
        this.repos = repos;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.imageRepository = imageRepository;
        this.proposalRepos = proposalRepos;
        this.proposalResponseMapper = proposalResponseMapper;
        this.categoryRepos = categoryRepos;
        this.categeoryResponseMapper = categeoryResponseMapper;
    }

    @Override
    public ServiceResponseDTO save(ServiceRequestDTO dto) {

        if(findServiceByName(dto.getName()))
            throw new ServiceAlreadyExistingException("This service already exist and cannot be created again");

        com.example.marketplace.entity.Service service=requestMapper.toEntity(dto);
        service.setCreatedAt(new Date());

        if (dto.getIdImage() != null) {
            Image image = imageRepository.findById(dto.getIdImage())
                    .orElseThrow(() -> new EntityNotFoundException("Image not found"));
            service.setImage(image);
        }
        service=repos.save(service);

        return responseMapper.toDTO(service);
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
        com.example.marketplace.entity.Service service=repos.findById(id).orElseThrow(
                ()->new ServiceNotFoundException("Service not found for this id")
        );

        return responseMapper.toDTO(service);

    }

    @Override
    public void deleteById(int id) {
        repos.deleteById(id);
    }


    @Override
    public List<ServiceResponseDTO> searchService(String name) {
        List<ServiceResponseDTO> results=new ArrayList<>();

        for(com.example.marketplace.entity.Service service:repos.searchService(name)){
            results.add(responseMapper.toDTO(service));
        }

        return results;
    }

    @Override
    public List<ServiceProposalResponseDTO> getProposalsByServiceId(int id) {
        if(getById(id)==null)
            throw new ServiceNotFoundException("There is no service with this id");

        List<ServiceProposalResponseDTO> proposals = new ArrayList<>();

        for(ServiceProposal proposal:proposalRepos.findByServiceId(id)){
            proposals.add(proposalResponseMapper.toDTO(proposal));
        }

        return proposals;
    }





    @Override
    public boolean findServiceByName(String name) {
        return repos.findByName(name).isPresent();
    }

    @Override
    public CategoryResponseDTO getServiceCategory(int id) {
        getById(id);

        Optional<Category> category = categoryRepos.findByServiceId(id);


        if(category.isPresent())
            return categeoryResponseMapper.toDTO(category.get());
        else
            throw new CategoryNotFoundException("Categoryt not found for this service");
    }


}

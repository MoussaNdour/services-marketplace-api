package com.example.marketplace.assembler;

import com.example.marketplace.controller.ServiceProposalController;
import com.example.marketplace.dto.response.ServiceProposalResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ServiceProposalAssembler implements RepresentationModelAssembler<ServiceProposalResponseDTO, EntityModel<ServiceProposalResponseDTO>> {

    @Override
    public EntityModel<ServiceProposalResponseDTO> toModel(ServiceProposalResponseDTO entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                ServiceProposalController.class
                        ).getServiceProposalById(entity.getId())
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ServiceProposalController.class)
                                .getProviderByProposalId(entity.getId())
                ).withRel("provider"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ServiceProposalController.class)
                                .getServiceByProposalId(entity.getId())
                ).withRel("service")

        );
    }

    @Override
    public CollectionModel<EntityModel<ServiceProposalResponseDTO>> toCollectionModel(Iterable<? extends ServiceProposalResponseDTO> entities) {
        CollectionModel<EntityModel<ServiceProposalResponseDTO>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ServiceProposalController.class).getServiceProposals()
                ).withSelfRel()
        );

        return collectionModel;
    }
}

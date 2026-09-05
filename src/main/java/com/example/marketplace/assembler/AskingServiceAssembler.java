package com.example.marketplace.assembler;

import com.example.marketplace.controller.AskingServiceController;
import com.example.marketplace.dto.response.AskingResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;


@Component
public class AskingServiceAssembler implements RepresentationModelAssembler<AskingResponseDTO, EntityModel<AskingResponseDTO>> {
    @Override
    public EntityModel<AskingResponseDTO> toModel(AskingResponseDTO entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                AskingServiceController.class
                        ).getServiceAskingById(entity.getId())
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                AskingServiceController.class
                        ).getAskingClient(entity.getId())
                ).withRel("client"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                AskingServiceController.class
                        ).getAskingPropoSal(entity.getId())
                ).withRel("proposal")
        );
    }

    @Override
    public CollectionModel<EntityModel<AskingResponseDTO>> toCollectionModel(Iterable<? extends AskingResponseDTO> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}

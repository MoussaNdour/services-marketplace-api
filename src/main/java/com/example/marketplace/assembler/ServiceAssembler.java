package com.example.marketplace.assembler;

import com.example.marketplace.controller.ServiceController;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;


@Component
public class ServiceAssembler implements RepresentationModelAssembler<ServiceResponseDTO, EntityModel<ServiceResponseDTO>> {

    @Override
    public EntityModel<ServiceResponseDTO> toModel(ServiceResponseDTO entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                ServiceController.class
                        ).getServiceById(entity.getId())
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                ServiceController.class
                        ).getServiceCategory(entity.getId())
                ).withRel("category")

        );
    }

    @Override
    public CollectionModel<EntityModel<ServiceResponseDTO>> toCollectionModel(Iterable<? extends ServiceResponseDTO> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}

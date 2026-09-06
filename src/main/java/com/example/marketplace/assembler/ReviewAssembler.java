package com.example.marketplace.assembler;

import com.example.marketplace.dto.response.ReviewResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class ReviewAssembler implements RepresentationModelAssembler<ReviewResponseDTO, EntityModel<ReviewResponseDTO>>{
    @Override
    public EntityModel<ReviewResponseDTO> toModel(ReviewResponseDTO entity) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<ReviewResponseDTO>> toCollectionModel(Iterable<? extends ReviewResponseDTO> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}

package com.example.marketplace.mapper.response;

import com.example.marketplace.dto.response.ReviewResponseDTO;
import com.example.marketplace.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewResponseMapper {

    @Mapping(source = "comment.content", target = "comment")
    ReviewResponseDTO toDto(Review review);
}
package com.example.marketplace.mapper.request;

import com.example.marketplace.dto.request.ReviewRequestDTO;
import com.example.marketplace.dto.request.ReviewUpdateDTO;
import com.example.marketplace.entity.Review;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewRequestMapper {

    @Mapping(source = "comment", target = "comment.content")
    @Mapping(target = "askingservice", ignore = true)
    Review toEntity(ReviewRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "comment", target = "comment.content")
    Review partialUpdate(ReviewUpdateDTO reviewRequestDTO, @MappingTarget Review review);
}
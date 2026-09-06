package com.example.marketplace.mapper.request;


import com.example.marketplace.dto.request.AskingRequestDTO;
import com.example.marketplace.dto.request.AskingUpdateDTO;
import com.example.marketplace.entity.Asking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AskingServiceRequestMapper {

    @Mapping(target = "proposal", ignore = true)
    @Mapping(target = "client", ignore = true)
    Asking toEntity(AskingRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "proposal", ignore = true)
    @Mapping(target = "createdat", ignore = true)
    void updateEntityFromDto(AskingUpdateDTO dto, @MappingTarget Asking asking);
}

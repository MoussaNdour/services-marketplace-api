package com.example.marketplace.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServiceRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer idcategory;
}

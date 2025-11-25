package com.example.marketplace.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer idcategory;
}

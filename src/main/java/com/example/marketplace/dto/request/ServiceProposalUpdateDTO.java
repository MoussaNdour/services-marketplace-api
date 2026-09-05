package com.example.marketplace.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceProposalUpdateDTO {

    @Min(value = 100)
    private double price;

    @NotBlank
    private String description;
}

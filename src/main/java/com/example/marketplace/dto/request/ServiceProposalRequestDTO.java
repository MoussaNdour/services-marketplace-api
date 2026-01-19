package com.example.marketplace.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceProposalRequestDTO {

    @Min(value = 1, message = "This key is invalide")
    private int idservice;

    @Min(value = 1, message = "This key is invalid")
    private int idprovider;
}

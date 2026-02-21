package com.example.marketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceProposalResponseDTO {

    private int id;

    private String serviceName;

    private String providerEmail;

    private String providerFirstName;

    private String providerLastName;

    private double price;

    private String description;

    private String serviceCategory;
}

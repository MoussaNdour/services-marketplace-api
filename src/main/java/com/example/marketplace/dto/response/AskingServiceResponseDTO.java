package com.example.marketplace.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AskingServiceResponseDTO {

    private int id;

    private String serviceName;

    private String clientFirstName;

    private String clientLastName;

}

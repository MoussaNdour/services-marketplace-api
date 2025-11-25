package com.example.marketplace.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceResponseDTO {

    private int id;


    private String name;


    private Date createdAt;


    private String category;
}

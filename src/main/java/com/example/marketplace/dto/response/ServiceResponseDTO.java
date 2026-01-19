package com.example.marketplace.dto.response;


import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServiceResponseDTO {

    private int id;

    private String name;

    private Date createdAt;

    private String category;

    private String description;

    private double mark;

    private String imagePath;


}

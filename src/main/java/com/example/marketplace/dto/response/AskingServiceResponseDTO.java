package com.example.marketplace.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AskingServiceResponseDTO {

    private int id;

    private String status;

    private Date createdAt;

    private Date scheduledAt;

}

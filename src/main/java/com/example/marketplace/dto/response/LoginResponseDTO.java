package com.example.marketplace.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String refreshToken;
    private Object profile;
}

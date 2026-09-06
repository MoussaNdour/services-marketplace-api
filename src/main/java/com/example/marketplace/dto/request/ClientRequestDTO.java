package com.example.marketplace.dto.request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonTypeName("CLIENT")
public class ClientRequestDTO extends RegistrationRequest{

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    private String profession;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}

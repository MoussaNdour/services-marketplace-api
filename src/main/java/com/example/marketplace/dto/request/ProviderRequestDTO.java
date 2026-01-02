package com.example.marketplace.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonTypeName("PROVIDER")
public class ProviderRequestDTO extends RegistrationRequest{

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String profession;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}

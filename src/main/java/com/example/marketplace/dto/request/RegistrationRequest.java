package com.example.marketplace.dto.request;


import com.example.marketplace.type.Role;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "role",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRequestDTO.class, name = "CLIENT"),
        @JsonSubTypes.Type(value = ProviderRequestDTO.class, name = "PROVIDER")
})

@NoArgsConstructor
@AllArgsConstructor
public abstract class RegistrationRequest {
    @NotNull
    public Role role;
}


package com.example.marketplace.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.marketplace.entity.Review}
 */
@AllArgsConstructor
@Data
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRequestDTO implements Serializable {
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    Integer mark;

    @NotNull
    Integer askingserviceId;

    @NotBlank
    String comment;
}
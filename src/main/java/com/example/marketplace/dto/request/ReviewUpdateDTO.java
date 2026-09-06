package com.example.marketplace.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@AllArgsConstructor
@Data
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewUpdateDTO {

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    Integer mark;


    @NotBlank
    String comment;
}

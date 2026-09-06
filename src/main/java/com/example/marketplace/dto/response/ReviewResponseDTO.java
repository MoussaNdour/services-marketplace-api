package com.example.marketplace.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.marketplace.entity.Review}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewResponseDTO implements Serializable {
    Integer id;

    Integer mark;

    String comment;
}
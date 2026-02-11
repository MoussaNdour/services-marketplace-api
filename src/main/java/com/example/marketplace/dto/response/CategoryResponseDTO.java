package com.example.marketplace.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseDTO {

    private int id;

    private String name;

    @JsonProperty("_links")
    private Map<String,Map<String,String>> _links;
 }

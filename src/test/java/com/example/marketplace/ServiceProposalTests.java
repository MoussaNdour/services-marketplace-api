package com.example.marketplace;



import com.example.marketplace.dto.request.ServiceRequestDTO;
import com.example.marketplace.dto.response.ServiceResponseDTO;
import com.example.marketplace.service.MyUserDetailsService;
import com.example.marketplace.service.interfaces.ServiceForServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(com.example.marketplace.controller.Service.class)
public class ServiceProposalTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceForServiceInterface serviceForService;

    @MockitoBean
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test1() throws Exception {

        ServiceRequestDTO serviceRequestDTO= ServiceRequestDTO.builder()
                .name("Website with Angular and Bootstrap")
                .idcategory(1)
                .build();

        ServiceResponseDTO serviceResponseDTO=ServiceResponseDTO.builder()
                        .id(1)
                        .name("Website with Angular and Bootstrap")
                        .createdAt(new Date())
                        .category("website building")
                        .build();

        when(serviceForService.save(any(ServiceRequestDTO.class))).thenReturn(serviceResponseDTO);

        ResultActions resultActions =mockMvc.perform(post("/api/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceRequestDTO))
        );

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.service.id").value(serviceResponseDTO.getId()))
                .andExpect(jsonPath("$.service.name").value(serviceResponseDTO.getName()));
    }
}

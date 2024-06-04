package com.challenge.w2m.integrationTest.controller;

import com.challenge.w2m.dto.PaginatedResponseDto;
import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;
import com.challenge.w2m.service.SpacecraftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
 class SpacecraftControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SpacecraftService spacecraftService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findAllTest() throws Exception {
        PaginatedResponseDto<SpacecraftResponseDto> paginatedResponse = new PaginatedResponseDto<>();
        paginatedResponse.setContent(Collections.singletonList(new SpacecraftResponseDto()));

        when(spacecraftService.findAll(0, 10)).thenReturn(paginatedResponse);

        mockMvc.perform(get("/api/spacecraft?page=0&limit=10")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void findByIdTest() throws Exception {
        SpacecraftResponseDto spacecraftResponseDto = new SpacecraftResponseDto();
        spacecraftResponseDto.setId(1l);

        when(spacecraftService.findById(1L)).thenReturn(spacecraftResponseDto);

        mockMvc.perform(get("/api/spacecraft/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findByValueTest() throws Exception {
        List<SpacecraftResponseDto> spacecraftList = Collections.singletonList(new SpacecraftResponseDto());

        when(spacecraftService.findByName("Enterprise")).thenReturn(spacecraftList);

        mockMvc.perform(get("/api/spacecraft/findByName?value=Enterprise")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createTest() throws Exception {
        SpacecraftRequestDto spacecraftRequestDto = new SpacecraftRequestDto();

        mockMvc.perform(post("/api/spacecraft")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spacecraftRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTest() throws Exception {
        SpacecraftRequestDto spacecraftRequestDto =SpacecraftRequestDto.builder()
                .name("newName")
                .maxCrew (200)
                .maxSpeed(600d)
                .build();
        SpacecraftResponseDto spacecraftResponseDto = new SpacecraftResponseDto();
        spacecraftResponseDto.setId(1L);

        when(spacecraftService.update(any(), any())).thenReturn(spacecraftResponseDto);

        mockMvc.perform(put("/api/spacecraft/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(spacecraftRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());


    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/spacecraft/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

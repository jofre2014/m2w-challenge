package com.challenge.w2m.integrationTest.controller;


import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;
import com.challenge.w2m.enums.MediaType;
import com.challenge.w2m.service.MediaService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MediaControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private MediaService mediaService;

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
    void createMediaTest() throws Exception {
        MediaRequestDto mediaRequestDto = MediaRequestDto.builder()
                .title("Star Trek")
                .mediaType(MediaType.MOVIE)
                .build();

        mockMvc.perform(post("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mediaRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void findAllMediaTest() throws Exception {
        MediaResponseDto mediaResponseDto = MediaResponseDto.builder()
                .title("Star Trek")
                .mediaType(MediaType.MOVIE)
                .build();

        List<MediaResponseDto> mediaList = Collections.singletonList(mediaResponseDto);

        when(mediaService.findAll()).thenReturn(mediaList);

        mockMvc.perform(get("/api/media")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Star Trek"))
                .andExpect(jsonPath("$[0].media_type").value(MediaType.MOVIE.toString()));
    }
}
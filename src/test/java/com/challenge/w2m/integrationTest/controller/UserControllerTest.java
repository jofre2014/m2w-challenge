package com.challenge.w2m.integrationTest.controller;

import com.challenge.w2m.dto.AuthorityDto;
import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;
import com.challenge.w2m.service.impl.SecurityUserDetailService;
import com.challenge.w2m.utils.AuthorityName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SecurityUserDetailService securityUserDetailService;

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
    void findAllUsers() throws Exception {

        AuthorityDto authority = AuthorityDto.builder()
                .id(1l)
                .name(AuthorityName.ADMIN)
                .build();

        List<AuthorityDto> authorities = Collections.singletonList(authority);

        UserDto user = UserDto.builder()
                .id(1l)
                .username("usuarioTest")
                .authorities(authorities)
                .build();

        List<UserDto> users = Collections.singletonList(user);

        when(securityUserDetailService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/user")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("usuarioTest"));

        verify(securityUserDetailService, times(1)).getAllUsers();

    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void createUser() throws Exception {


        List<AuthorityName> authorities = Collections.singletonList(AuthorityName.ADMIN);

        UserRequest userRequest = UserRequest.builder()
                .username("userNew")
                .password("password")
                .authorities(authorities)
                .build();

        mockMvc.perform(post("/api/user")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        verify(securityUserDetailService, times(1)).create(any(UserRequest.class));
    }
}

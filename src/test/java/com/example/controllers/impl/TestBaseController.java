package com.example.controllers.impl;

import com.example.dto.LoggedUserDTO;
import com.example.filters.JWTAuthorizationFilter;
import com.example.repositories.UserRepository;
import com.example.services.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
abstract class TestBaseController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected JWTService jwtService;

    @SpyBean
    protected JWTAuthorizationFilter jwtAuthorizationFilter;

    protected MockHttpServletRequestBuilder buildPost(String url, Object dto) throws Exception {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto));
    }

    protected ResultMatcher checkError(String field, String message) {
        String expression = String.format("$.errors[?(@.field == '%s')].message", field);
        return jsonPath(expression).value(message);
    }

    protected Authentication getAuth(LoggedUserDTO dto, String role) {
        return new UsernamePasswordAuthenticationToken(dto, null, List.of(
                new SimpleGrantedAuthority(role)
        ));
    }

}

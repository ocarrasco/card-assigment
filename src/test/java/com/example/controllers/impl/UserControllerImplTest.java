package com.example.controllers.impl;

import com.example.dto.UserLoginRequestDTO;
import com.example.exceptions.UserNotFoundException;
import com.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerImpl.class)
class UserControllerImplTest extends TestBaseController {

    @MockBean
    private UserService userService;

    @Test
    void givenLoginRequestWhenValidCredentialsThenShouldBeOk() throws Exception {
        var dto = UserLoginRequestDTO.builder()
                .username("admin@email.com")
                .password("password")
                .build();

        when(userService.authenticate(any())).thenReturn("created-token");
        mockMvc.perform(buildPost("/users/auth", dto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("created-token"));
    }

    @Test
    void givenLoginRequestWhenMissingCredentialsThenShouldFail() throws Exception {
        var dto = UserLoginRequestDTO.builder().build();
        mockMvc.perform(buildPost("/users/auth", dto))
                .andExpect(status().isBadRequest())
                .andExpect(checkError("username", "NotEmpty"))
                .andExpect(checkError("password", "NotEmpty"));

        verifyNoInteractions(userService);
    }

    @Test
    void givenLoginRequestWhenUserNotFoundThenShouldFail() throws Exception {
        when(userService.authenticate(any()))
                .thenThrow(new UserNotFoundException("unknown"));

        var dto = UserLoginRequestDTO.builder()
                .username("admin@email.com")
                .password("password")
                .build();

        mockMvc.perform(buildPost("/users/auth", dto))
                .andExpect(status().isUnauthorized());
    }

}

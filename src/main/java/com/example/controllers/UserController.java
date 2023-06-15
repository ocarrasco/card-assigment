package com.example.controllers;

import com.example.dto.JwtResponseDTO;
import com.example.dto.UserLoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "user", description = "the user API")
public interface UserController {

    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve JWT when user exits in the system.")
    JwtResponseDTO authenticate(@Validated @RequestBody UserLoginRequestDTO userLoginRequestDTO);

}

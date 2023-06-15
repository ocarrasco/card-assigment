package com.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@Jacksonized
public class UserLoginRequestDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}

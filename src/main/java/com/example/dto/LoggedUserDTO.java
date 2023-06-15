package com.example.dto;

import com.example.entities.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoggedUserDTO {

    private Integer id;
    private String email;
    private UserRole role;

}

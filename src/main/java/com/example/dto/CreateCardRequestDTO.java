package com.example.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCardRequestDTO {

    @NotEmpty
    private String name;

    private String description;

    private String color;

}

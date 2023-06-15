package com.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CardResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private String color;
    private String status;
    private LocalDateTime createdAt;

}

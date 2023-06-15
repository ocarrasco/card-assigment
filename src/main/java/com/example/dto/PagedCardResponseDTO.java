package com.example.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PagedCardResponseDTO {

    private final List<CardResponseDTO> items;
    private final Integer totalPages;
    private final Long total;

}

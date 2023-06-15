package com.example.dto;

import com.example.dto.enums.SortAttribute;
import com.example.entities.enums.CardStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@ToString
public class CardSearchCriteriaDTO {

    // Filters attributes
    private String name;
    private String color;
    private CardStatus status;
    private Date dateOfCreation;

    // Pagination attributes
    private Integer page;
    private Integer size;

    // Sorting
    private SortAttribute[] sortProperties;
    private Sort.Direction sortDirection;

    public boolean hasFilters() {
        return hasName() || hasColor() || hasStatus() || hasDateOfCreation();
    }

    public boolean hasName() {
        return StringUtils.isNotEmpty(name);
    }

    public boolean hasColor() {
        return StringUtils.isNotEmpty(color);
    }

    public boolean hasStatus() {
        return status != null;
    }

    public boolean hasDateOfCreation() {
        return dateOfCreation != null;
    }

    public PageRequest toPageRequest() {
        return sortProperties != null && sortProperties.length > 0
                ? PageRequest.of(page, size, sortDirection, convertSorPropertiesToString())
                : PageRequest.of(page, size);
    }

    private String[] convertSorPropertiesToString() {
        return Stream.of(sortProperties).map(e -> e.getAttribute())
                .collect(Collectors.toList())
                .toArray(String[]::new);
    }

    public LocalDateTime convertDateToLocalDateTime() {
        return LocalDateTime.ofInstant(dateOfCreation.toInstant(), ZoneId.systemDefault());
    }

}


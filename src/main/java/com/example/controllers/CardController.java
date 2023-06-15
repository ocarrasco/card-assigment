package com.example.controllers;

import com.example.dto.*;
import com.example.dto.enums.SortAttribute;
import com.example.entities.enums.CardStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@Tag(name = "card", description = "the card API")
public interface CardController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve all card using optional parameters")
    PagedCardResponseDTO getCards(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String color,
            @Parameter(schema = @Schema(implementation = CardStatus.class)) @RequestParam(defaultValue = "", required = false) CardStatus status,
            @Parameter(example = "2020-12-31") @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateOfCreation,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size,
            @RequestParam(required = false) SortAttribute[] sortProperties,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    );

    @GetMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retrieve a card by given id")
    CardResponseDTO getCardById(@PathVariable Integer cardId);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create a card for passed user in token")
    CardResponseDTO createCard(
            @Validated @RequestBody CreateCardRequestDTO createCardRequestDTO,
            BindingResult bindingResult
    );

    @PutMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CardResponseDTO updateCard(
            @PathVariable Integer cardId,
            @Validated @RequestBody UpdateCardRequestDTO updateCardRequestDTO,
            BindingResult bindingResult
    );

    @DeleteMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    JSONMessageDTO deleteCard(@PathVariable Integer cardId);

}

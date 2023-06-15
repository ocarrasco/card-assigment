package com.example.controllers.impl;

import com.example.controllers.CardController;
import com.example.dto.*;
import com.example.dto.enums.SortAttribute;
import com.example.entities.enums.CardStatus;
import com.example.exceptions.BadRequestException;
import com.example.services.CardService;
import com.example.validations.CardRequestDTOValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardControllerImpl implements CardController {

    private final CardService cardService;
    private final CardRequestDTOValidator cardRequestDTOValidator;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public PagedCardResponseDTO getCards(
            String name, String color, CardStatus status, Date dateOfCreation,
            Integer page, Integer size,
            SortAttribute[] sortProperties, Sort.Direction sortDirection) {

        var searchCriteriaDTO = CardSearchCriteriaDTO.builder()
                .name(name).color(color).status(status)
                .dateOfCreation(dateOfCreation)
                .page(page).size(size)
                .sortProperties(sortProperties).sortDirection(sortDirection)
                .build();

        log.info("retrieving cards for criteria {}", searchCriteriaDTO);
        return cardService.getCards(getLoggedUser().getId(), searchCriteriaDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public CardResponseDTO getCardById(Integer cardId) {
        final Integer userId = getLoggedUser().getId();
        log.info("Getting cardId {} for userId {}", cardId, userId);
        return cardService.getById(userId, cardId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public CardResponseDTO createCard(CreateCardRequestDTO createCardRequestDTO, BindingResult bindingResult) {
        Integer userId = getLoggedUser().getId();
        log.info("Saving card for user {}", userId);
        checkRequest(createCardRequestDTO, cardRequestDTOValidator, bindingResult);
        return cardService.saveCard(userId, createCardRequestDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public CardResponseDTO updateCard(
            Integer cardId,
            UpdateCardRequestDTO updateCardRequestDTO,
            BindingResult bindingResult) {

        checkRequest(updateCardRequestDTO, cardRequestDTOValidator, bindingResult);
        Integer userId = getLoggedUser().getId();
        log.info("Updating card {} for userId {}", cardId, userId);
        return cardService.updateCard(userId, cardId, updateCardRequestDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    public JSONMessageDTO deleteCard(Integer cardId) {
        Integer userId = getLoggedUser().getId();
        log.info("Deleting cardId {} for userId {}", cardId, userId);
        cardService.deleteCard(userId, cardId);
        return new JSONMessageDTO("ok");
    }

    protected LoggedUserDTO getLoggedUser() {
        return (LoggedUserDTO) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    protected void checkRequest(Object target, Validator validator, BindingResult bindingResult) {
        validator.validate(target, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult);
        }
    }

}

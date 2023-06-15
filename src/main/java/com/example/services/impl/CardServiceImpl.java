package com.example.services.impl;

import com.example.dto.*;
import com.example.entities.Card;
import com.example.entities.enums.CardStatus;
import com.example.exceptions.CardNotFoundException;
import com.example.mappings.CardDtoMapper;
import com.example.repositories.CardRepository;
import com.example.repositories.UserRepository;
import com.example.repositories.specs.CardSpecification;
import com.example.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDtoMapper cardDtoMapper;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardResponseDTO getById(Integer userId, Integer cardId) {
        var card = getCard(cardId, userId);
        return cardDtoMapper.fromEntity(card);
    }

    public PagedCardResponseDTO getCards(Integer userId, CardSearchCriteriaDTO cardSearchCriteriaDTO) {
        Specification<Card> spec = new CardSpecification(cardSearchCriteriaDTO);
        if (!userRepository.isAdmin(userId)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.join("owner").get("id"), userId));
        }

        var page = cardRepository.findAll(spec, cardSearchCriteriaDTO.toPageRequest());
        return PagedCardResponseDTO.builder()
                .items(cardDtoMapper.fromEntities(page.toList()))
                .totalPages(page.getTotalPages())
                .total(page.getTotalElements())
                .build();
    }

    public CardResponseDTO saveCard(Integer userId, CreateCardRequestDTO createCardRequestDTO) {
        Card createdCard = cardDtoMapper.fromRequest(createCardRequestDTO);
        createdCard.setStatus(CardStatus.TO_DO);
        createdCard.setOwner(userRepository.getReferenceById(userId));
        return cardDtoMapper.fromEntity(cardRepository.save(createdCard));
    }

    public CardResponseDTO updateCard(Integer userId, Integer cardId, UpdateCardRequestDTO updateCardRequestDTO) {
        var card = getCard(userId, cardId);
        cardDtoMapper.updateEntity(card, updateCardRequestDTO);
        return cardDtoMapper.fromEntity(cardRepository.save(card));
    }

    public void deleteCard(Integer userId, Integer cardId) {
        var card = getCard(userId, cardId);
        cardRepository.delete(card);
    }

    private Card getCard(Integer userId, Integer cardId) {
        var item = userRepository.isAdmin(userId)
                ? cardRepository.findById(cardId)
                : cardRepository.findByIdAndOwnerId(cardId, userId);

        return item.orElseThrow(() -> new CardNotFoundException(cardId, userId));
    }

}

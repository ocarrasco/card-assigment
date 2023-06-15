package com.example.services;

import com.example.dto.*;

public interface CardService {

    CardResponseDTO getById(Integer userId, Integer cardId);

    PagedCardResponseDTO getCards(Integer userId, CardSearchCriteriaDTO cardSearchCriteriaDTO);

    CardResponseDTO saveCard(Integer userId, CreateCardRequestDTO createCardRequestDTO);

    CardResponseDTO updateCard(Integer userId, Integer cardId, UpdateCardRequestDTO updateCardRequestDTO);

    void deleteCard(Integer userId, Integer cardId);

}

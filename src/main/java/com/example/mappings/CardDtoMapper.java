package com.example.mappings;

import com.example.dto.CardResponseDTO;
import com.example.dto.CreateCardRequestDTO;
import com.example.dto.UpdateCardRequestDTO;
import com.example.entities.Card;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CardDtoMapper {

    Card fromRequest(CreateCardRequestDTO createCardRequestDTO);

    CardResponseDTO fromEntity(Card card);

    List<CardResponseDTO> fromEntities(List<Card> cardList);

    void updateEntity(@MappingTarget Card card, UpdateCardRequestDTO updateCardRequestDTO);

}

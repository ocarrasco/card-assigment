package com.example.services.impl;

import com.example.entities.Card;
import com.example.exceptions.CardNotFoundException;
import com.example.mappings.CardDtoMapper;
import com.example.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @InjectMocks
    private CardServiceImpl cardService;

    @Spy
    private CardDtoMapper cardDtoMapper = Mappers.getMapper(CardDtoMapper.class);

    @Mock
    private CardRepository cardRepository;

    @Test
    void givenCardSearchWhenItemFoundThenShouldReturnOk() {
        var card = new Card();
        card.setId(1);
        card.setName("Important Stuff");
        card.setColor("#ff22a2");
        when(cardRepository.findByIdAndOwnerId(any(), any())).thenReturn(Optional.of(card));
        var dtoResponse = cardService.getById(1, 1);
        assertNotNull(dtoResponse);
        assertEquals(1, dtoResponse.getId());
        assertEquals("Important Stuff", dtoResponse.getName());
        assertEquals("#ff22a2", dtoResponse.getColor());
        assertNull(dtoResponse.getDescription());
    }

    @Test
    void givenCardSearchWhenNotFoundThenShouldThrowCardNotFoundException() {
        when(cardRepository.findByIdAndOwnerId(any(), any())).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.getById(1, 1));
        verifyNoInteractions(cardDtoMapper);
    }

}
package com.example.controllers.impl;

import com.example.dto.LoggedUserDTO;
import com.example.entities.enums.UserRole;
import com.example.services.CardService;
import com.example.validations.CardRequestDTOValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardControllerImpl.class)
class CardControllerImplTest extends TestBaseController {

    private static final String CARD_PATH = "/cards";

    @MockBean
    private CardService cardService;

    @MockBean
    private CardRequestDTOValidator cardRequestDTOValidator;

    @Test
    @WithMockUser("user@email.com")
    void givenGetCardsRequestWhenWrongStatusThenShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(CARD_PATH)
                .param("status", "UNKNOWN")
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(cardRequestDTOValidator, cardService);
    }

    @Test
    @WithMockUser("user@email.com")
    void givenGetCardsRequestWhenWrongDateOfCreationThenShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(CARD_PATH)
                .param("dateOfCreation", "1010-1010-101010")
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(cardRequestDTOValidator, cardService);
    }

    @Test
    @WithMockUser(value = "user@email.com")
    void givenGetCardsRequestWhenWrongSortPropertiesThenShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(CARD_PATH)
                .param("sortProperties", "WRONG")
        ).andExpect(status().isBadRequest());
        verifyNoInteractions(cardRequestDTOValidator, cardService);
    }


    @Test
    void givenGetCardsRequestWhenWrongRoleThenShouldBeForbidden() throws Exception {
        var loggedUser = LoggedUserDTO.builder()
                .id(1)
                .email("admin@email.com")
                .role(UserRole.ADMIN)
                .build();

        SecurityContextHolder.getContext().setAuthentication(getAuth(loggedUser, "UNKNOWN"));
        mockMvc.perform(get(CARD_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());

        verifyNoInteractions(cardRequestDTOValidator, cardService);
    }

    @Test
    void givenGetCardsRequestWhenRoleMemberThenShouldBeOk() throws Exception {
        var loggedUser = LoggedUserDTO.builder()
                .id(2)
                .email("member1@email.com")
                .role(UserRole.MEMBER)
                .build();

        SecurityContextHolder.getContext().setAuthentication(getAuth(loggedUser, "MEMBER"));
        mockMvc.perform(get(CARD_PATH)).andExpect(status().isOk());
        verify(cardService).getCards(eq(2), any());
    }

}

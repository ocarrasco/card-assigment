package com.example.dto;

import com.example.entities.enums.CardStatus;
import lombok.Data;

@Data
public class UpdateCardRequestDTO extends CreateCardRequestDTO {

    private CardStatus status;

}

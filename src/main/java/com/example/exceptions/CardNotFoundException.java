package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardNotFoundException extends RuntimeException {

    private final Integer cardId;
    private final Integer userId;

}

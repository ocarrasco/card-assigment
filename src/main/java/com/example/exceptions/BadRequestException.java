package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {

    private final BindingResult bindingResult;

}

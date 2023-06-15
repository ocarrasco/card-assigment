package com.example.controllers.utils;

import com.example.dto.JSONError;
import com.example.dto.JSONMessageDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.CardNotFoundException;
import com.example.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public JSONMessageDTO handleUserNotFoundException(UserNotFoundException e) {
        log.error("User not found {}", e.getUsername());
        return new JSONMessageDTO(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public JSONError handleBadRequestException(BadRequestException e) {
        var jsonError = new JSONError();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            jsonError.addError(fieldError.getField(), fieldError.getCode());
        }
        return jsonError;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CardNotFoundException.class)
    public JSONMessageDTO handleCardNotFoundException(CardNotFoundException e) {
        log.error("Card Id {} not found for owner {}", e.getCardId(), e.getUserId());
        return new JSONMessageDTO(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var jsonError = new JSONError();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            jsonError.addError(fieldError.getField(), fieldError.getCode());
        }
        return jsonError;
    }

}

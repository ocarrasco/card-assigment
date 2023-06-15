package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class JSONError {

    private final List<FieldError> errors;

    public JSONError() {
        errors = new LinkedList<>();
    }

    public void addError(String field, String value) {
        errors.add(new FieldError(field, value));
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private final String field;
        private final String message;
    }

}

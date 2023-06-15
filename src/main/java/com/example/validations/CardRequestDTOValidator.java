package com.example.validations;

import com.example.dto.CreateCardRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CardRequestDTOValidator implements Validator {

    private static final String VALID_COLOR_PATTERN = "^#[a-zA-Z0-9]{6}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateCardRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var dto = (CreateCardRequestDTO) target;
        if (dto.getColor() != null && !isColorValid(dto.getColor())) {
            errors.rejectValue("color", "invalid.format");
        }
    }

    protected boolean isColorValid(String color) {
        return !color.isEmpty() && color.matches(VALID_COLOR_PATTERN);
    }

}

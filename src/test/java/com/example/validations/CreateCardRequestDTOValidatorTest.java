package com.example.validations;

import com.example.dto.CreateCardRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateCardRequestDTOValidatorTest {

    @InjectMocks
    private CardRequestDTOValidator validator;

    @Test
    void givenDtoValidationWhenColorIsNullThenShouldBeOk() {
        var dto = new CreateCardRequestDTO();
        dto.setColor(null);
        var errors = new BeanPropertyBindingResult(dto, "dto");
        validator.validate(dto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void givenDtoValidationWhenColorIsEmptyThenShouldHaveError() {
        var dto = new CreateCardRequestDTO();
        dto.setColor("");
        var errors = new BeanPropertyBindingResult(dto, "dto");
        validator.validate(dto, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    void givenDtoValidationWhenColorFormatErrorThenShouldHaveColorError() {
        var dto = new CreateCardRequestDTO();
        dto.setColor("#12345_");
        var errors = new BeanPropertyBindingResult(dto, "dto");
        validator.validate(dto, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("color", errors.getFieldErrors().get(0).getField());
        assertEquals("invalid.format", errors.getFieldErrors().get(0).getCode());
    }

    @Test
    void givenDtoValidationWhenColorFormatOkThenShouldBeOk() {
        var dto = new CreateCardRequestDTO();
        dto.setColor("#12eA56");
        var errors = new BeanPropertyBindingResult(dto, "dto");
        validator.validate(dto, errors);
        assertFalse(errors.hasErrors());
    }

}

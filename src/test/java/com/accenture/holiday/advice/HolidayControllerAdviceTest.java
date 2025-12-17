package com.accenture.holiday.advice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HolidayControllerAdviceTest {

    private final HolidayControllerAdvice advice = new HolidayControllerAdvice();

    @Test
    void badRequestException_convertsBindingErrors_toMap() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult br = Mockito.mock(BindingResult.class);
        FieldError f = new FieldError("obj", "field", "must not be null");
        when(br.getFieldErrors()).thenReturn(List.of(f));
        when(ex.getBindingResult()).thenReturn(br);

        Map<String, String> map = advice.badRequestException(ex);
        assertThat(map).containsEntry("field", "must not be null");
    }

    @Test
    void otherException_returnsMessage() {
        RuntimeException ex = new RuntimeException("oops");
        String res = advice.otherException(ex);
        assertThat(res).isEqualTo("oops");
    }
}


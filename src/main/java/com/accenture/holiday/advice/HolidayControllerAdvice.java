package com.accenture.holiday.advice;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class HolidayControllerAdvice {

    /**
     * Handler that converts MethodArgumentNotValidException into a 400 response with
     * a map of field -> error message. This is useful for bean validation failures.
     *
     * @param exception the validation exception thrown by Spring
     * @return a map where the key is the invalid field and the value is the validation message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badRequestException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        return errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    /**
     * Generic handler for RuntimeException that returns the exception message with 400 status.
     *
     * @param exception runtime exception captured from controllers
     * @return the exception message to be returned to the client
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String otherException(RuntimeException exception) {
        return exception.getMessage();
    }
}

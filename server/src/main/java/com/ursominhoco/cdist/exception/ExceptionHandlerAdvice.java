package com.ursominhoco.cdist.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    // 400

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        BindingResult bindingResult = exception.getBindingResult();
        Map<String,String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Erro de validação!", request.getServletPath(), errors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidationException(ConstraintViolationException exception, HttpServletRequest request) {
        Set<ConstraintViolation<?>> bindingResult = exception.getConstraintViolations();
        Map<String,String> errors = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : bindingResult) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Erro de validação!", request.getServletPath(), errors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidationException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        HttpInputMessage bindingResult = exception.getHttpInputMessage();
        Map<String, String> errors = new HashMap<>();
        errors.put(HttpInputMessage.class.toString(), bindingResult.toString());
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "Erro de validação!", request.getServletPath(), errors);
    }

    // 401

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handlerValidationException(AuthenticationException exception, HttpServletRequest request) {
        String bindingResult = exception.getLocalizedMessage();
        Map<String, String> errors = new HashMap<>();
        errors.put(String.class.toString(), bindingResult);
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), "Erro de autorização!", request.getServletPath(), errors);
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handlerValidationException(BadCredentialsException exception, HttpServletRequest request) {
        String bindingResult = exception.getLocalizedMessage();
        Map<String, String> errors = new HashMap<>();
        errors.put(String.class.toString(), bindingResult);
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), "Erro de autorização!", request.getServletPath(), errors);
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handlerValidationException(InsufficientAuthenticationException exception, HttpServletRequest request) {
        String bindingResult = exception.getLocalizedMessage();
        Map<String, String> errors = new HashMap<>();
        errors.put(String.class.toString(), bindingResult);
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), "Erro de autorização!", request.getServletPath(), errors);
    }

    // 409

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handlerValidationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        String bindingResult = exception.getLocalizedMessage();
        Map<String, String> errors = new HashMap<>();
        errors.put(String.class.toString(), bindingResult);
        return new ApiError(HttpStatus.CONFLICT.value(), "Erro de conflito!", request.getServletPath(), errors);
    }
}
package com.ursominhoco.logistica.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    // 400

    @ExceptionHandler(CepInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerCepInvalido(CepInvalidoException e, HttpServletRequest request) {
        return ApiError.of(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidacao(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> erros = new LinkedHashMap<>();
        for (FieldError erro : e.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }
        return ApiError.of(HttpStatus.BAD_REQUEST, "Erro de validação!", request.getRequestURI(), erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerCorpoIlegivel(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ApiError.of(HttpStatus.BAD_REQUEST, "Erro de validação!", request.getRequestURI());
    }

    // 404

    @ExceptionHandler(CepNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlerCepNaoEncontrado(CepNaoEncontradoException e, HttpServletRequest request) {
        return ApiError.of(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI());
    }

    // 502

    @ExceptionHandler(ServicoCepIndisponivelException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError handlerServicoIndisponivel(ServicoCepIndisponivelException e, HttpServletRequest request) {
        return ApiError.of(HttpStatus.BAD_GATEWAY, e.getMessage(), request.getRequestURI());
    }
}
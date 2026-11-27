package com.ursominhoco.logistica.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public record ApiError(
        long timestamp,
        int status,
        String message,
        String url,
        Map<String, String> validationErrors
) {
    public static ApiError of(HttpStatus status, String mensagem, String url) {
        return new ApiError(System.currentTimeMillis(), status.value(), mensagem, url, null);
    }

    public static ApiError of(HttpStatus status, String mensagem, String url, Map<String, String> erros) {
        return new ApiError(System.currentTimeMillis(), status.value(), mensagem, url, erros);
    }
}
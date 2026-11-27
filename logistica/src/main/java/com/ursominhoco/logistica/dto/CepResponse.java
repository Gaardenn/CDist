package com.ursominhoco.logistica.dto;

public record CepResponse(
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String estado
) {
}
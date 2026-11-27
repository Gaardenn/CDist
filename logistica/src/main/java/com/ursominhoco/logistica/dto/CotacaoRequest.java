package com.ursominhoco.logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CotacaoRequest (
    @NotBlank(message = "O CEP de destino é obrigatório")
    String cepDestino,

    @NotNull(message = "A quantidade de itens físicos é obrigatória")
    @PositiveOrZero(message = "A quantidade de itens físicos não pode ser negativa")
    Integer itensFisicos
) {
}
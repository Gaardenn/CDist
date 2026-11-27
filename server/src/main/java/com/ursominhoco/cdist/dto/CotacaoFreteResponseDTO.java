package com.ursominhoco.cdist.dto;

import java.math.BigDecimal;

/** Resposta do POST /frete/cotacao da API de logística */
public record CotacaoFreteResponseDTO(BigDecimal valor, Integer prazoDias, String regiao) {
}
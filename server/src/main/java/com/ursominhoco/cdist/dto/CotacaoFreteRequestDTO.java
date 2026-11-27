package com.ursominhoco.cdist.dto;

/** Corpo do POST /frete/cotacao da API de logística */
public record CotacaoFreteRequestDTO(String cepDestino, int itensFisicos) {
}
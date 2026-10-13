package com.ursominhoco.cdist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoResponseDTO {
    private Long produtoId;
    private String nome;
    private String imagem;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal desconto;
    private BigDecimal precoFinal;
    private BigDecimal subtotal;
}

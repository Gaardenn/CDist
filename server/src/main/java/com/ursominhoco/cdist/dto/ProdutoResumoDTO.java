package com.ursominhoco.cdist.dto;

import com.ursominhoco.cdist.enums.Plataforma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResumoDTO {
    private Long id;
    private String nome;
    private Plataforma plataforma;
    private String imagem;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal precoFinal;
    private String status;
}

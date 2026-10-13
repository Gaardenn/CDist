package com.ursominhoco.cdist.dto;

import com.ursominhoco.cdist.enums.Classificacao;
import com.ursominhoco.cdist.enums.Midia;
import com.ursominhoco.cdist.enums.Plataforma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDetalheDTO {
    private Long id;
    private String nome;
    private CategoriaResumoDTO categoria;
    private Plataforma plataforma;
    private String desenvolvedora;
    private String distribuidora;
    private Midia midia;
    private Integer estoque;
    private BigDecimal preco;
    private BigDecimal desconto;
    private BigDecimal precoFinal;
    private String imagem;
    private Classificacao classificacao;
    private LocalDate dataLancamento;
    private String descricao;
    private Double avaliacao;
    private Integer quantidadeAvaliacoes;
    private Map<String, Integer> distribuicaoAvaliacoes;
}

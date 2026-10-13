package com.ursominhoco.cdist.entity;

import com.ursominhoco.cdist.enums.Classificacao;
import com.ursominhoco.cdist.enums.Midia;
import com.ursominhoco.cdist.enums.Plataforma;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @NotNull
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Plataforma plataforma;

    @NotNull
    private String desenvolvedora;

    @NotNull
    private String distribuidora;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Midia midia;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull
    @Column(precision = 3, scale = 2)
    private BigDecimal desconto;

    @NotNull
    private String imagem;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Classificacao classificacao;

    @NotNull
    private LocalDate dataLancamento;

    @NotNull
    @Column(length = 2000)
    private String descricao;

    private int quantidadeAvaliacoes;

    private int avaliacaoNotaUm;
    private int avaliacaoNotaDois;
    private int avaliacaoNotaTres;
    private int avaliacaoNotaQuatro;
    private int avaliacaoNotaCinco;

    @NotNull
    private Integer estoque;
}

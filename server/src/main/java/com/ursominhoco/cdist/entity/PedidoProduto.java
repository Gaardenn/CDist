package com.ursominhoco.cdist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "pedido_produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Min(1)
    private int quantidade;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @NotNull
    @Column(precision = 3, scale = 2)
    private BigDecimal desconto;

    public BigDecimal getPrecoFinal() {
        BigDecimal fator = BigDecimal.ONE.subtract(desconto);
        return precoUnitario.multiply(fator).setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getSubtotal() {
        return getPrecoFinal().multiply(BigDecimal.valueOf(quantidade));
    }
}

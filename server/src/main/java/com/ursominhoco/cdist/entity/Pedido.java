package com.ursominhoco.cdist.entity;

import com.ursominhoco.cdist.enums.MetodoPagamento;
import com.ursominhoco.cdist.enums.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull
    private LocalDateTime data;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @NotNull
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    private String cidade;

    @NotNull
    @Column(length = 2)
    private String estado;

    @NotNull
    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal frete = BigDecimal.ZERO;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal total;

}

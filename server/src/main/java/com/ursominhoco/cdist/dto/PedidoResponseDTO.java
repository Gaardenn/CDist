package com.ursominhoco.cdist.dto;

import com.ursominhoco.cdist.entity.Endereco;
import com.ursominhoco.cdist.enums.MetodoPagamento;
import com.ursominhoco.cdist.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime data;
    private StatusPedido status;
    private MetodoPagamento metodoPagamento;
    private Endereco endereco;
    private List<ItemPedidoResponseDTO> itens;
    private BigDecimal frete;
    private BigDecimal total;
}

package com.ursominhoco.cdist.dto;

import com.ursominhoco.cdist.enums.MetodoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    @NotNull
    private Long enderecoId;

    @NotNull
    private MetodoPagamento metodoPagamento;

    @NotNull
    @Valid
    private List<ItemPedidoRequestDTO> itens;
}

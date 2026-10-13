package com.ursominhoco.cdist.mapper;

import com.ursominhoco.cdist.dto.EnderecoPedidoDTO;
import com.ursominhoco.cdist.dto.ItemPedidoResponseDTO;
import com.ursominhoco.cdist.dto.PedidoResponseDTO;
import com.ursominhoco.cdist.entity.Pedido;
import com.ursominhoco.cdist.entity.PedidoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoMapper {
    @Mapping(target = "endereco", source = ".")
    PedidoResponseDTO paraDto(Pedido pedido);

    EnderecoPedidoDTO paraEnderecoDto(Pedido pedido);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nome", source = "produto.nome")
    @Mapping(target = "imagem", source = "produto.imagem")
    ItemPedidoResponseDTO paraItemDto(PedidoProduto item);
}

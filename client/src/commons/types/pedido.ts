import type { EnderecoPedidoDTO } from "./endereco";
import type { MetodoPagamento, StatusPedido } from "./enums";

export interface ItemPedidoRequestDTO {
    produtoId: number;
    quantidade: number; // mínimo 1
}

/** POST /pedidos */
export interface PedidoRequestDTO {
    enderecoId: number;
    metodoPagamento: MetodoPagamento;
    itens: ItemPedidoRequestDTO[]; // não pode ser vazio
}

export interface ItemPedidoResponseDTO {
    produtoId: number;
    nome: string;
    imagem: string;
    quantidade: number;
    precoUnitario: number;
    desconto: number; // 0.20
    precoFinal: number;
    subtotal: number;
}

export interface PedidoResponseDTO {
    id: number;
    data: string; // "2026-11-24T17:30:00"
    status: StatusPedido;
    metodoPagamento: MetodoPagamento;
    endereco: EnderecoPedidoDTO;
    itens: ItemPedidoResponseDTO[];
    frete: number;
    total: number;
}
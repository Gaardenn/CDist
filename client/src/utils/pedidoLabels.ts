import type { MetodoPagamento, StatusPedido } from "@/commons/types";

export const STATUS_PEDIDO: Record<StatusPedido, string> = {
    PENDENTE: "Pendente",
    REALIZADO: "Realizado",
    EM_TRANSITO: "Em trânsito",
    ENVIADO: "Enviado",
};

export const METODO_PAGAMENTO: Record<MetodoPagamento, string> = {
    PIX: "Pix",
    BOLETO: "Boleto",
    CARTAO_DE_CREDITO: "Cartão de crédito",
};
/** GET e POST /enderecos */
export interface EnderecoDTO {
    id?: number; // no POST é ignorado
    cep: string;
    logradouro: string;
    numero: string;
    complemento?: string;
    bairro: string;
    cidade: string;
    estado: string;
}

/** Cópia do endereço no pedido */
export type EnderecoPedidoDTO = Omit<EnderecoDTO, "id">;
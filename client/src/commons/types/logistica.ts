/** GET /cep/{cep} */
export interface CepResponseDTO {
    cep: string;
    logradouro: string;
    bairro: string;
    cidade: string;
    estado: string;
}

/** POST /frete/cotacao */
export interface CotacaoFreteRequestDTO {
    cepDestino: string;
    itensFisicos: number;  // soma das quantidades dos itens de mídia física
}

export interface CotacaoFreteResponseDTO {
    valor: number;
    prazoDias: number;
    regiao: string | null;  // null quando não existe nenhum item físico
}
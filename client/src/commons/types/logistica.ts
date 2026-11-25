// RASCUNHO

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
    cep: string;
    quantidadeItensFisicos: number;
}

export interface CotacaoFreteResponseDTO {
    regiao: string;
    valor: number;
    prazoDias: number;
}
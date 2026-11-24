import type { Classificacao, Midia, Plataforma, StatusEstoque } from "./enums";

export interface CategoriaDTO {
    id: number;
    nome: string;
    slug: string;
    descricao: string;
    imagem: string; // URL
}

export interface CategoriaResumoDTO {
    id: number;
    nome: string;
    slug: string;
}

/** Jogo simples (GET /produtos) */
export interface ProdutoResumoDTO {
    id: number;
    nome: string;
    plataforma: Plataforma;
    imagem: string; // URL
    preco: number;
    desconto: number; // 0.20 = 20%
    precoFinal: number; // já com desconto pelo cálculo do backend
    status: StatusEstoque;
}

/** Jogo completo (GET /produtos/{id}) */
export interface ProdutoDetalhesDTO {
    id: number;
    nome: string;
    categoria: CategoriaResumoDTO;
    plataforma: Plataforma;
    desenvolvedora: string;
    distribuidora: string;
    midia: Midia;
    estoque: number;
    preco: number;
    desconto: number; // 0.20
    precoFinal: number;
    imagem: string;
    classificacao: Classificacao;
    dataLancamento: string; // "2022-11-09"
    descricao: string;
    avaliacao: number; // média 0-5
    quantidadeAvaliacoes: number;
    distribuicaoAvaliacoes: Record<string, number>; // chaves "5" a "1"
}

export interface ListarProdutosParams {
    categoriaId?: number;
    page?: number;
    size?: number;
}
export type Jogo = {
    id: number | string;
    nome: string;
    preco: number;
    desconto?: number;
    imagem: string;
    plataforma: string;
    tags: string[];
    dev: string;
    pub: string;
    midia: string;
    classificacao: number | string;
    data: string;
    modo: string;
    descricao: string;
};

export type CartItem = Jogo & {
    quantidade: number;
    marcado?: boolean;
    presente?: boolean;
}
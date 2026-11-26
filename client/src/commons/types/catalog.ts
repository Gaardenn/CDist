export type StatusJogo = "Em estoque" | "Esgotado";

export type Jogo = {
    id: number | string;
    nome: string;
    preco: number;
    desconto?: number; // em porcentagem (20 = 20%)
    imagem: string; // URL completa
    plataforma: string; // "PlayStation 4"
    status?: StatusJogo;
    estoque?: number; // só com o GET /produtos/{id}
    // Só em GET /produtos/{id}
    dev?: string;
    pub?: string;
    midia?: string;
    classificacao?: string;
    data?: string;
    descricao?: string;
    // API atual não envia
    tags?: string[];
    modo?: string;
    peso?: number;
    volume?: number;
};

export type CartItem = Jogo & {
    quantidade: number;
    marcado?: boolean;
    presente?: boolean;
}
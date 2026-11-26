import type {
    Classificacao, Jogo, Midia, Plataforma, ProdutoDetalheDTO, ProdutoResumoDTO, StatusEstoque, StatusJogo
} from "@/commons/types";

const PLATAFORMAS: Record<Plataforma, string> = {
    PLAYSTATION_1: "PlayStation 1",
    PLAYSTATION_2: "PlayStation 2",
    PLAYSTATION_3: "PlayStation 3",
    PLAYSTATION_4: "PlayStation 4",
    PLAYSTATION_5: "PlayStation 5",
    XBOX_360: "Xbox 360",
    XBOX_ONE: "Xbox One",
    XBOX_SERIES_X: "Xbox Series X",
    NINTENDO_SWITCH: "Nintendo Switch",
    NINTENDO_WII_U: "Nintendo Wii U",
    NINTENDO_64: "Nintendo 64",
    SUPER_NINTENDO: "Super Nintendo",
    MEGA_DRIVE: "Mega Drive",
};

const MIDIAS: Record<Midia, string> = {
    FISICA: "Física",
    DIGITAL: "Digital",
};

const CLASSIFICACOES: Record<Classificacao, string> = {
    LIVRE: "Livre",
    DEZ: "10 anos",
    DOZE: "12 anos",
    QUATORZE: "14 anos",
    DEZESSEIS: "16 anos",
    DEZOITO: "18 anos",
};

const STATUS: Record<StatusEstoque, StatusJogo> = {
    EM_ESTOQUE: "Em estoque",
    ESGOTADO: "Esgotado",
};

/** API manda 0.20, vira 20 para os componentes */
const descontoEmPorcentagem = (desconto: number): number => Math.round((desconto ?? 0) * 100);

/** API manda "2022-11-09", vira "9 de novembro de 2022" para os componentes */
const formatarData = (iso: string): string =>
    new Date(`${iso}T00:00:00`).toLocaleDateString("pt-BR", { day: "numeric",  month: "long", year: "numeric" });

/** GET /produtos */
export function produtoResumoParaJogo(dto: ProdutoResumoDTO): Jogo {
    return {
        id: dto.id,
        nome: dto.nome,
        plataforma: PLATAFORMAS[dto.plataforma] ?? dto.plataforma,
        imagem: dto.imagem,
        preco: dto.preco,
        desconto: descontoEmPorcentagem(dto.desconto),
        status: STATUS[dto.status],
    };
}

/** GET /produtos/{id} */
export function produtoDetalheParaJogo(dto: ProdutoDetalheDTO): Jogo {
    return {
        id: dto.id,
        nome: dto.nome,
        plataforma: PLATAFORMAS[dto.plataforma] ?? dto.plataforma,
        imagem: dto.imagem,
        preco: dto.preco,
        desconto: descontoEmPorcentagem(dto.desconto),
        status: dto.estoque > 0 ? "Em estoque" : "Esgotado",
        estoque: dto.estoque,
        dev: dto.desenvolvedora,
        pub: dto.distribuidora,
        midia: MIDIAS[dto.midia] ?? dto.midia,
        classificacao: CLASSIFICACOES[dto.classificacao] ?? dto.classificacao,
        data: formatarData(dto.dataLancamento),
        descricao: dto.descricao,
        // A API não envia peso e volume ainda (frete é da logísitca). Por enquanto tá
        // zerado pro carrinho poder fazer as contas.
        peso: 0,
        volume: 0,
    };
}
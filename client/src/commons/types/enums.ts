export type Plataforma =
  | "PLAYSTATION_1" | "PLAYSTATION_2" | "PLAYSTATION_3" | "PLAYSTATION_4" | "PLAYSTATION_5"
  | "XBOX_360" | "XBOX_ONE" | "XBOX_SERIES_X"
  | "NINTENDO_SWITCH" | "NINTENDO_WII_U" | "NINTENDO_64" | "SUPER_NINTENDO"
  | "MEGA_DRIVE";

export type Midia = "FISICA" | "DIGITAL";

export type Classificacao = "LIVRE" | "DEZ" | "DOZE" | "QUATORZE" | "DEZESSEIS" | "DEZOITO";

export type MetodoPagamento = "PIX" | "BOLETO" | "CARTAO_DE_CREDITO";

export type StatusPedido = "PENDENTE" | "REALIZADO" | "EM_TRANSITO" | "ENVIADO";

/** Campo 'status' do ProdutoResumoDTO como texto */
export type StatusEstoque = "EM_ESTOQUE" | "ESGOTADO";
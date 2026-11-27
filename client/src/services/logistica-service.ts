import type {
    CepResponseDTO, CotacaoFreteRequestDTO, CotacaoFreteResponseDTO, IResponse
} from "@/commons/types";
import { apiLogistica } from "@/lib/axios";
import { request } from "./helpers";

/** GET /cep/{cep} (aceita com - ou sem). 404 CEP não existe, 502 ViaCEP fora do ar */
const buscarCep = (cep: string): Promise<IResponse<CepResponseDTO>> =>
  request(() => apiLogistica.get<CepResponseDTO>(`/cep/${cep.replace(/\D/g, "")}`),
    "CEP encontrado!", "CEP não encontrado");

/** POST /frete/cotacao para exibir, valor oficial é gravado direto pelo backend no pedido */
const cotarFrete = (dados: CotacaoFreteRequestDTO): Promise<IResponse<CotacaoFreteResponseDTO>> =>
  request(() => apiLogistica.post<CotacaoFreteResponseDTO>("/frete/cotacao", {
    ...dados,
    cepDestino: dados.cepDestino.replace(/\D/g, ""),
  }), "Frete calculado!", " Não foi poss'ivel calcular o frete");

const LogisticaService = { buscarCep, cotarFrete };
export default LogisticaService;
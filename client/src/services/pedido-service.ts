import type { IResponse, PedidoRequestDTO, PedidoResponseDTO } from "@/commons/types";
import { apiPrincipal } from "@/lib/axios";
import { request } from "./helpers";

const URL = "/pedidos";  // exige token (o interceptor do axios envia o header)

/** Mais recentes primeiro. O back já ordena, mas o client não depende dele */
const maisRecentePrimeiro = (a: PedidoResponseDTO, b: PedidoResponseDTO): number =>
  new Date(b.data).getTime() - new Date(a.data).getTime() || b.id - a.id;

const listar = async (): Promise<IResponse<PedidoResponseDTO[]>> => {
  const resposta = await request(() => apiPrincipal.get<PedidoResponseDTO[]>(URL),
    "Pedidos carregados com sucesso!", "Falha ao carregar os pedidos");

    if (resposta.success && resposta.data) {
      return { ...resposta, data: [...resposta.data].sort(maisRecentePrimeiro) };
    }
    return resposta;
};

const buscarPorId = (id: number): Promise<IResponse<PedidoResponseDTO>> =>
  request(() => apiPrincipal.get<PedidoResponseDTO>(`${URL}/${id}`),
    "Pedido carregado com sucesso!", "Pedido não encontrado");

/** O client envia só endereço, pagamento e itens. O back pede o frete para a logística e calcula o total.
    Erros: 404 (endereço ou produto), 409 (estoque), 503 (logística fora do ar), 400 (CEP sem frete) */
const criar = (pedido: PedidoRequestDTO): Promise<IResponse<PedidoResponseDTO>> =>
  request(() => apiPrincipal.post<PedidoResponseDTO>(URL, pedido),
    "Pedido realizado com sucesso!", "Não foi possível finalizar o pedido");

const PedidoService = { listar, buscarPorId, criar };
export default PedidoService;
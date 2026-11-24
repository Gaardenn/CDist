import type { IResponse, PedidoRequestDTO, PedidoResponseDTO } from "@/commons/types";
import { apiPrincipal } from "@/lib/axios";
import { request } from "./helpers";

const URL = "/pedidos"; //exige token

const listar = (): Promise<IResponse<PedidoResponseDTO[]>> =>
  request(() => apiPrincipal.get<PedidoResponseDTO[]>(URL),
    "Pedidos carregados com sucesso!", "Falha ao carregar os pedidos");

const buscarPorId = (id: number): Promise<IResponse<PedidoResponseDTO>> =>
  request(() => apiPrincipal.get<PedidoResponseDTO>(`${URL}/${id}`),
    "Pedido carregado com sucesso!", "Pedido não encontrado");

/** O back e calcula e o frete só é preenchido com a API de logísitica */
const criar = (pedido: PedidoRequestDTO): Promise<IResponse<PedidoResponseDTO>> =>
  request(() => apiPrincipal.post<PedidoResponseDTO>(URL, pedido),
    "Pedido realizado com sucesso!", "Não foi possível finalizar o pedido");

const PedidoService = { listar, buscarPorId, criar };
export default PedidoService;
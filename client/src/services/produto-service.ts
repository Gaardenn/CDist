import type {
    IPagedModel, IResponse, ListarProdutosParams, ProdutoDetalheDTO, ProdutoResumoDTO
} from "@/commons/types";
import { apiPrincipal } from "@/lib/axios";
import { request } from "./helpers";

const URL = "/produtos"; // público

/** GET paginado */
const listar = (params: ListarProdutosParams = {}): Promise<IResponse<IPagedModel<ProdutoResumoDTO>>> =>
  request(() => apiPrincipal.get<IPagedModel<ProdutoResumoDTO>>(URL, { params }),
    "Produtos carregados com sucesso!", "Falha ao carregar os produtos");

const buscarPorId = (id: number): Promise<IResponse<ProdutoDetalheDTO>> =>
  request(() => apiPrincipal.get<ProdutoDetalheDTO>(`${URL}/${id}`),
    "Produto carregado com sucesso!", "Produto não encontrado");

const ProdutoService = { listar, buscarPorId };
export default ProdutoService;
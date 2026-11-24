import type { CategoriaDTO, IResponse } from "@/commons/types";
import { apiPrincipal } from "@/lib/axios";
import { request } from "./helpers";

const listar = (): Promise<IResponse<CategoriaDTO[]>> =>
  request(() => apiPrincipal.get<CategoriaDTO[]>("/categorias"),
    "Categorias carregadas com sucesso!", "Falha ao carregar as categorias");

const CategoriaService = { listar };
export default CategoriaService;
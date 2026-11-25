import type { EnderecoDTO, IResponse } from "@/commons/types";
import { apiPrincipal } from "@/lib/axios";
import { request } from "./helpers";

const URL = "/enderecos"; // precisa de token

// O back não tem PUT nem DELETE de endereço
const listar = (): Promise<IResponse<EnderecoDTO[]>> =>
  request(() => apiPrincipal.get<EnderecoDTO[]>(URL),
    "Endereços carregados com sucesso!", "Falha ao carregar os endereços");

const buscarPorId = (id: number): Promise<IResponse<EnderecoDTO>> =>
  request(() => apiPrincipal.get<EnderecoDTO>(`${URL}/${id}`),
    "Endereço carregado com sucesso!", "Endereço não encontrado");

/** CEP só com digitos */
const criar = (endereco: EnderecoDTO): Promise<IResponse<EnderecoDTO>> =>
  request(() => apiPrincipal.post<EnderecoDTO>(URL, { ...endereco, cep: endereco.cep.replace(/\D/g, "") }),
    "Endereço salvo com sucesso!", "Falha ao salvar o endereço");

const EnderecoService = { listar, buscarPorId, criar };
export default EnderecoService;
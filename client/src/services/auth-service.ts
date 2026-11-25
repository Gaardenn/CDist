import type { AuthenticationResponse, AuthRequestDTO, IResponse, MensagemResponse, UsuarioDTO } from "@/commons/types";
import { request } from "./helpers";
import { apiPrincipal } from "@/lib/axios";

const URL = "/usuarios";

const cadastrar = (usuario: UsuarioDTO): Promise<IResponse<MensagemResponse>> =>
    request(() => apiPrincipal.post<MensagemResponse>(`${URL}/cadastro`, usuario), "Usuário salvo com sucesso!", "Falha ao salvar o usuário");

const entrar = (credenciais: AuthRequestDTO): Promise<IResponse<AuthenticationResponse>> =>
    request(() => apiPrincipal.post<AuthenticationResponse>(`${URL}/login`, credenciais), "Login realizado com sucesso!", "Falha ao fazer login");

const AuthService = { cadastrar, entrar };
export default AuthService;


/** Corpo do POST /usuarios/login */
export interface AuthRequestDTO {
    email: string;
    senha: string;
}

/** Corpo do POST /usuarios/cadastro */
export interface UsuarioDTO {
    nome: string;
    email: string;
    senha: string;
}

export interface AuthorityResponseDTO {
    autoridade: string;
}

export interface UsuarioResponseDTO {
    nome: string;
    email: string;
    authorities: AuthorityResponseDTO[];
}

/** Resposta do login */
export interface AuthenticationResponse {
    token: string;
    usuario: UsuarioResponseDTO;
}

/** Resposta simples do back */
export interface MensagemResponse {
    message: string;
}
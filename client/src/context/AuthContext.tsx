import type { AuthenticationResponse, UsuarioResponseDTO } from "@/commons/types";
import { createContext, type ReactNode } from "react";
import { apiPrincipal } from "@/lib/axios";

const CHAVE_TOKEN = "cdist:token";
const CHAVE_USUARIO = "cdist:usuario";

interface AuthContextType {
    autenticado: boolean;
    usuario: UsuarioResponseDTO | null;
    login: (resposta: AuthenticationResponse) => void;
    logout: () => void;
}

interface AuthProviderProps {
    children: ReactNode;
}

const AuthContext = createContext<AuthContextType | null>(null);

function aplicarToken(token: string | null) {
    if (token == null) {
        delete apiPrincipal.defaults.headers.common["Authorization"];
    } 

    apiPrincipal.defaults.headers.common["Authorization"] = `Bearer ${token}`;
}
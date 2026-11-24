import type { Jogo } from "@/commons/types";

/**
 * @deprecated Lê o catálogo antigo do json
 * Some quando o catálogo vier da API.
 */
export async function carregarJogosLocal(): Promise<Jogo[]> {
    const response = await fetch("/jogos.json");

    if (!response.ok) {
        throw new Error("Não foi possível carregar os produtos");
    }

    return await response.json() as Promise<Jogo[]>;
}
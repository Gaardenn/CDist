import type { Jogo } from "@/commons/types";

export async function ProductService(): Promise<Jogo[]> {
    const response = await fetch("/jogos.json");

    if (!response.ok) {
        throw new Error("Não foi possível carregar os produtos");
    }

    return await response.json() as Promise<Jogo[]>;
}
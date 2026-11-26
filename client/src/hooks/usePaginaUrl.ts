import { useSearchParams } from "react-router-dom";

/** A página vem da URL (?page=2, começa em 1). Devolve o índice da API (começa em 0). */
export function usePaginaUrl() {
    const [searchParams, setSearchParams] = useSearchParams();
    const valor = Number(searchParams.get("page"));
    const pagina = Number.isInteger(valor) && valor > 1 ? valor - 1 : 0;

    const irParaPagina = (novaPagina: number) => {
        setSearchParams((atual) => {
            const novo = new URLSearchParams(atual); // preserva o resto dos parâmetros
            novo.set("page", String(novaPagina + 1));
            return novo;
        });
    };

    return { pagina, irParaPagina };
}
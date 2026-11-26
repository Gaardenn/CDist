import { useState, useEffect, useCallback, useRef } from "react";
import ProdutoService from "@/services/produto-service";
import { produtoDetalheParaJogo } from "@/utils/produtoAdapter";
import type { Jogo } from "@/commons/types";

export function useProduct(id?: string) {
    const [produto, setProduto] = useState<Jogo | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [naoEncontrado, setNaoEncontrado] = useState<boolean>(false);
    const requisicaoAtual = useRef(0);

    const carregar = useCallback(async (): Promise<void> => {
        const requisicao = ++requisicaoAtual.current;
        const produtoId = Number(id);

        setProduto(null);
        setError(null);
        setNaoEncontrado(false);

        // id inválido não vai para a API
        if (!Number.isInteger(produtoId) || produtoId <= 0) {
            setNaoEncontrado(true);
            setLoading(false);
            return;
        }

        setLoading(true);
        const res = await ProdutoService.buscarPorId(produtoId);
        if (requisicao !== requisicaoAtual.current) return;

        if (res.success && res.data) {
            setProduto(produtoDetalheParaJogo(res.data));
        } else if (res.status === 404) {
            setNaoEncontrado(true);
        } else {
            setError(res.status === 0 ? res.message : "Não foi possível carregar o produto. Tente novamente.");
        }
        setLoading(false);
    }, [id]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    return { produto, loading, error, naoEncontrado, retry: carregar };
}
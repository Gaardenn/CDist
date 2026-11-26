import { useState, useEffect, useCallback, useRef } from "react";
import ProdutoService from "@/services/produto-service";
import { produtoResumoParaJogo } from "@/utils/produtoAdapter";
import type { IPagedModel, Jogo, ProdutoResumoDTO } from "@/commons/types";

type UseProductsParams = {
    categoriaId?: number;
    page?: number; // começa em 0
    size?: number;
    enabled?: boolean; // se for false não busca
};

export function useProducts({ categoriaId, page = 0, size = 12, enabled = true }: UseProductsParams = {}) {
    const [products, setProducts] = useState<Jogo[]>([]);
    const [pageInfo, setPageInfo] = useState<IPagedModel<ProdutoResumoDTO>["page"] | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const requisicaoAtual = useRef(0);

    const carregar = useCallback(async (): Promise<void> => {
        if (!enabled) return;
        const id = ++requisicaoAtual.current;
        setLoading(true);
        setError(null);

        const res = await ProdutoService.listar({ categoriaId, page, size });
        if (id !== requisicaoAtual.current) return; // sobrou de uma busca antiga

        if (res.success && res.data) {
            setProducts(res.data.content.map(produtoResumoParaJogo));
            setPageInfo(res.data.page);
        } else {
            setError(res.status === 0 ? res.message : "Não foi possível carregar os produtos. Tente novamente.");
        }
        setLoading(false);
    }, [categoriaId, page, size, enabled]);

    useEffect(() => {
        carregar();
    }, [carregar]);

    return { products, pageInfo, loading: enabled && loading, error, retry: carregar };
}
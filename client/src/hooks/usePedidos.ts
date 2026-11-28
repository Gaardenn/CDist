import { useState, useEffect, useCallback, useRef } from "react";
import PedidoService from "@/services/pedido-service";
import type { PedidoResponseDTO } from "@/commons/types";

export function usePedidos() {
    const [pedidos, setPedidos] = useState<PedidoResponseDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [naoAutenticado, setNaoAutenticado] = useState<boolean>(false);
    const requisicaoAtual = useRef(0);

    const carregar = useCallback(async (): Promise<void> => {
        const requisicao = ++requisicaoAtual.current;
        setLoading(true);
        setError(null);
        setNaoAutenticado(false);

        const res = await PedidoService.listar();
        if (requisicao !== requisicaoAtual.current) return;  // resposta de uma busca antiga

        if (res.success && res.data) {
            setPedidos(res.data);
        } else if (res.status === 401) {
            setNaoAutenticado(true);
        } else {
            setError(res.status === 0 ? res.message : "Não foi possível carregar seus pedidos. Tente novamente.");
        }
        setLoading(false);
    }, []);

    useEffect(() => {
        carregar();
    }, [carregar]);

    return { pedidos, loading, error, naoAutenticado, retry: carregar };
}
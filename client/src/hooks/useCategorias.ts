import { useState, useEffect, useCallback } from "react";
import CategoriaService from "@/services/categoria-service";
import type { CategoriaDTO } from "@/commons/types";

export function useCategorias() {
    const [categorias, setCategorias] = useState<CategoriaDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const carregar = useCallback(async (): Promise<void> => {
        setLoading(true);
        setError(null);
        const res = await CategoriaService.listar();
        if (res.success && res.data) {
            setCategorias(res.data);
        } else {
            setError(res.status === 0 ? res.message : "Não foi possível carregar as categorias. Tente novamente.");
        }
        setLoading(false);
    }, []);

    useEffect(() => {
        carregar();
    }, [carregar]);

    return { categorias, loading, error, retry: carregar };
}
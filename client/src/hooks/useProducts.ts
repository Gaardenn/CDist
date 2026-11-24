import { useState, useEffect, useCallback } from "react";
import { ProductService } from '../services/ProductService';
import type { Jogo } from "@/commons/types";

export function useProducts() {
    const [products, setProducts] = useState<Jogo[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const loadProducts = useCallback(async (): Promise<void> => {
        setLoading(true);
        setError(null);
        try {
            const data: Jogo[] = await ProductService();
            setProducts(data);
        } catch (err) {
            console.error("Erro ao carregar produtos ", err);
            setError("Não foi possível carregar os produtos. Tente novamente.");
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        loadProducts();
    }, [loadProducts]);

    const getProduct = (productId: Jogo["id"]): Jogo | undefined => {
        return products.find((product) => product.id === productId);
    }

    return {products, loading, error, getProduct, retry: loadProducts};
}
import { useState, useEffect, useCallback } from "react";
import { ProductService } from '../services/ProductService';

export function useProducts() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const loadProducts = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await ProductService();
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

    const getProduct = (productId) => {
        return products.find((product) => product.id === productId);
    }

    return {products, loading, error, getProduct, retry: loadProducts};
}
import type { CartItem, Jogo } from "@/commons/types";
import { useState, useEffect } from "react"

export function useCart() {
    const [cart, setCart] = useState<CartItem[]>(() => {
        if (typeof window === "undefined") return [];

        try {
            const salvo = localStorage.getItem("carrinho");
            return salvo ? JSON.parse(salvo) as CartItem[] : [];
        } catch {
            return [];
        }
    });

    useEffect(() => {
        localStorage.setItem("carrinho", JSON.stringify(cart));
    }, [cart]);

    const adicionarProduto = (produto: Jogo, quant: number): boolean => {
        const existe = cart.find((p) => p.id === produto.id);

        if (existe) {
            setCart(cart.map((p) => p.id === produto.id ?
                { ...p, quantidade: p.quantidade + quant } :
                p));

            return false;
        } else {
            setCart([...cart, { ...produto, quantidade: quant }]);

            return true;
        }
    }

    const adicionarUnidade = (produto: CartItem) => {
        setCart(cart.map((p) => p.id === produto.id ? { ...p, quantidade: p.quantidade + 1 } : p));
    }

    const removerUnidade = (produto: CartItem) => {
        setCart(cart.map((p) => p.id === produto.id ? { ...p, quantidade: p.quantidade - 1 } : p));
    }

    const removerItem = (produto: CartItem) => {
        setCart(cart.filter(p => p.id !== produto.id));
    }

    const marcar = (produto: CartItem) => {
        setCart(cart.map((p) => p.id === produto.id ? { ...p, marcado: !produto.marcado} : p));
    }

    const todosMarcados = (): boolean => {
        let todos = true;

        cart.map((p) => p.marcado === false ? todos = false : true);

        return todos;
    }

    const marcarTudo = (boolean: boolean) => {
        cart.map((p) => p.marcado = boolean);
    }

    const presentear = (produto: CartItem) => {
        setCart(cart.map((p) => p.id === produto.id ? { ...p, presente: !produto.presente } : p));
    }

    const quantMarcados = (): number => {
        let quant = 0;

        cart.map((p) => p.marcado === true ? quant=quant+p.quantidade : quant);

        return quant;
    }

    const quantTotal = (): number => {
        let quant = 0;

        cart.map((p) => quant = quant + p.quantidade);

        return quant;
    }

    const calcularSubtotal = (): number => {
        let subtotal = 0;

        cart.map((p) => p.marcado === true ? subtotal += p.preco * p.quantidade : subtotal);

        return subtotal;
    }

    const calcularDescontoTotal = (): number => {
        let desconto = 0;

        cart.map((p) => p.marcado === true ? desconto += p.desconto * p.quantidade : desconto);

        return desconto;
    }

    const calcularFreteTotal = (): number => {
        let frete = 0;

        const itensFisicos = cart.filter((p) => p.marcado === true && p.midia === "Física");

        const totais = itensFisicos.reduce((acumulador, p) => {
            return {
                peso: acumulador.peso + p.peso,
                volume: acumulador.volume + p.volume,
                quant: acumulador.quant + p.quantidade
            };
        }, { peso: 0, volume: 0, quant: 0 });

        const { peso, volume, quant } = totais;

        if (itensFisicos.length !== 0) {
            frete = 7.9 + (peso * 2.5) + (volume * 1.2) - ((quant - 1) * 0.5);
        }

        return Math.min(frete, 29.9);
    }

    return { cart, adicionarProduto, adicionarUnidade, removerUnidade, removerItem, marcar, todosMarcados, marcarTudo, presentear, quantMarcados, quantTotal, calcularSubtotal,
        calcularDescontoTotal, calcularFreteTotal };
}
import { useEffect, useState } from 'react';
import type { Jogo } from '@/commons/types';
import ProdutoService from '@/services/produto-service';
import { produtoDetalheParaJogo } from '@/utils/produtoAdapter';
import '../styles/UltimosAcessados.css';
import { GameCard } from './GameCard';

type UltimosAcessadosProps = {
    queue?: number[];
}

export function UltimosAcessados({ queue = [] }: UltimosAcessadosProps) {
    const [produtos, setProdutos] = useState<Jogo[]>([]);
    const chave = queue.join(",");

    useEffect(() => {
        let cancelado = false;
        const ids = chave ? chave.split(",").map(Number) : [];

        Promise.all(ids.map((id) => ProdutoService.buscarPorId(id))).then((respostas) => {
            if (cancelado) return;
            const lista = respostas
                .filter((r) => r.success && r.data)
                .map((r) => produtoDetalheParaJogo(r.data!));
            setProdutos(lista.reverse()); // pra deixar o mais recente primeiro
        });

        return () => { cancelado = true; };
    }, [chave]);

    if (produtos.length === 0) {
        return null;
    }

    return (
        <div className="ultimos-acessados-container">
            <h2 className="ultimos-acessados-title">ÚLTIMOS ACESSADOS</h2>
            <div className="ultimos-acessados-grid">
                {produtos.map((produto) => (
                    <GameCard key={produto.id} jogo={produto} />
                ))}
            </div>
        </div>
    );
}
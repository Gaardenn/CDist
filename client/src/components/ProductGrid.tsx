import type { ReactNode } from "react";
import type { Jogo } from "@/commons/types";
import { GameCard } from "./GameCard";
import { Loader } from "./Loader";
import { ErrorState } from "./ErrorState";

type ProductGridProps = {
    products: Jogo[];
    loading: boolean;
    error: string | null;
    retry: () => void;
    classeGrade: string;
    vazio: ReactNode;
    mensagemCarregando?: string;
};

export function ProductGrid({ products, loading, error, retry, classeGrade, vazio, mensagemCarregando = "Carregando..." }: ProductGridProps) {
    if (loading) return <Loader mensagem={mensagemCarregando} />;
    if (error) return <ErrorState mensagem={error} onRetry={retry} />;
    if (products.length === 0) return <>{vazio}</>;

    return (
        <div className={classeGrade}>
            {products.map((p) => (
                <GameCard key={p.id} jogo={p} />
            ))}
        </div>
    )
}
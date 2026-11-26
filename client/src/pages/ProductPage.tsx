import { useEffect } from 'react'
import { ProductDetails } from '../components/ProductDetails';
import { ProductPrice } from '../components/ProductPrice';
import '../styles/ProductPage.css';
import Footer from "../components/Footer"
import Navbar from "../components/Navbar"
import { useProduct} from '../hooks/useProduct';
import { useParams } from 'react-router-dom';
import { Loader } from '../components/Loader';
import { ErrorState } from '../components/ErrorState';
import type { Jogo, CartItem } from "@/commons/types";

type ProductPageProps = {
    adicionarProduto: (produto: Jogo, quant: number) => boolean;
    quantTotal: () => number;
    cart: CartItem[];
    adicionarProdutoFila: (produto: Jogo) => void;
}

export function ProductPage({adicionarProduto, quantTotal, cart, adicionarProdutoFila}: ProductPageProps) {
    const { id } = useParams<{ id: string }>();
    const { produto, loading, error, naoEncontrado, retry } = useProduct(id);

    useEffect(() => {
        if (produto) {
            adicionarProdutoFila(produto);
        }
    }, [produto]);

    if (loading) {
        return (
            <>
                <Navbar quantTotal={quantTotal} cart={cart} />
                <Loader mensagem="Carregando produto..." />
                <Footer />
            </>
        );
    }

    if (error) {
        return (
            <>
                <Navbar quantTotal={quantTotal} cart={cart} />
                <ErrorState mensagem="Produto não encontrado." />
                <Footer />
            </>
        )
    }

    if (naoEncontrado || !produto) {
        return (
            <>
                <Navbar quantTotal={quantTotal} cart={cart} />
                <ErrorState mensagem="Produto não encontrado." />
                <Footer />
            </>
        )
    }

    return (
        <>
            <Navbar quantTotal={quantTotal} cart={cart} />
            <div className="product-page">
                <div className="product-page-wrapper">
                    <main className="product-main-content">
                        <ProductDetails produto={produto} />
                    </main>
                    <aside className="product-sidebar">
                        <ProductPrice produto={produto} adicionarProduto={adicionarProduto}/>
                    </aside>
                </div>
            </div>
        </>
    )
}
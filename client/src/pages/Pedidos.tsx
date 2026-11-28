import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { Loader } from "../components/Loader";
import { ErrorState } from "../components/ErrorState";
import { PedidoCard } from "../components/PedidoCard";
import { usePedidos } from "../hooks/usePedidos";
import "../styles/Pedidos.css";
import type { CartItem } from "@/commons/types";

type PedidosProps = {
    quantTotal: () => number;
    cart: CartItem[];
};

export function Pedidos({ quantTotal, cart }: PedidosProps) {
    const { pedidos, loading, error, naoAutenticado, retry } = usePedidos();

    const mostrarLista = !loading && !error && !naoAutenticado;

    return (
        <div className="page">
            <Navbar quantTotal={quantTotal} cart={cart} />

            <main className="pedidos-body">
                <h1 className="pedidos-title">Meus pedidos</h1>

                {loading && <Loader mensagem="Carregando seus pedidos..." />}

                {!loading && naoAutenticado && (
                    <div className="pedidos-empty">
                        <p>Entre na sua conta para ver seus pedidos.</p>
                        <Link to="/autenticacao" className="pedidos-button">Entrar</Link>
                    </div>
                )}

                {!loading && error && <ErrorState mensagem={error} onRetry={retry} />}

                {mostrarLista && pedidos.length === 0 && (
                    <div className="pedidos-empty">
                        <p>Você ainda não fez nenhum pedido.</p>
                        <Link to="/" className="pedidos-button">Ir para a loja</Link>
                    </div>
                )}

                {mostrarLista && pedidos.length > 0 && (
                    <ul className="pedidos-lista">
                        {pedidos.map((pedido) => (
                            <li key={pedido.id}>
                                <PedidoCard pedido={pedido} />
                            </li>
                        ))}
                    </ul>
                )}
            </main>

            <Footer />
        </div>
    );
}
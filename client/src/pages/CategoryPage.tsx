import { useParams, Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { Loader } from "../components/Loader";
import { ErrorState } from "../components/ErrorState";
import { ProductGrid } from "../components/ProductGrid";
import { Pagination } from "../components/Pagination";
import { useCategorias } from "../hooks/useCategorias";
import { useProducts } from "../hooks/useProducts";
import { usePaginaUrl } from "../hooks/usePaginaUrl";
import "../styles/CategoryPage.css";
import type { CartItem } from "@/commons/types";

type CategoryPageProps = {
    quantTotal: () => number;
    cart: CartItem[];
};

// Só o visual fica aqui. As outras informações vêm da API (GET /categorias)
const CLASSES_VISUAIS: Record<string, string> = {
    playstation: "playstation",
    xbox: "xbox",
    nintendo: "nintendo",
    sega: "retro",
};

export function CategoryPage({ quantTotal, cart }: CategoryPageProps) {
    const { slug } = useParams<{ slug: string }>();
    const { pagina, irParaPagina } = usePaginaUrl();
    const { categorias, loading: carregandoCategorias, error: erroCategorias, retry: retryCategorias } = useCategorias();

    const categoria = categorias.find((c) => c.slug === slug);
    const { products, pageInfo, loading, error, retry } = useProducts({
        categoriaId: categoria?.id,
        page: pagina,
        enabled: !!categoria,
    });

    function trocarPagina(novaPagina: number): void {
        irParaPagina(novaPagina); // o slug continua na URL, o filtro continua
        document.getElementById("catalogo-categoria")?.scrollIntoView({ behavior: "smooth" });
    }

    if (carregandoCategorias) {
        return (
            <div className="page">
                <Navbar quantTotal={quantTotal} cart={cart} />
                <Loader mensagem="Carregando categoria..." />
                <Footer />
            </div>
        );
    }

    if (erroCategorias) {
        return (
            <div className="page">
                <Navbar quantTotal={quantTotal} cart={cart} />
                <ErrorState mensagem={erroCategorias} onRetry={retryCategorias} />
                <Footer />
            </div>
        );
    }

    if (!categoria) {
        return (
            <div className="page">
                <Navbar quantTotal={quantTotal} cart={cart} />
                <div className="category-not-found">
                    <h1>Categoria não encontrada</h1>
                    <Link to="/">Voltar para a home</Link>
                </div>
                <Footer />
            </div>
        );
    }

    const classe = CLASSES_VISUAIS[categoria.slug] ?? "retro";

    return (
        <div className="page">
            <Navbar quantTotal={quantTotal} cart={cart} />

            <section className={`category-banner category-banner-${classe}`}>
                <div className="category-banner-content">
                    <img src={categoria.imagem} alt={categoria.nome} className="category-banner-logo" />
                    <div className="category-banner-text">
                        <h1 className="category-banner-title">{categoria.nome}</h1>
                        <p className="category-banner-subtitle">{categoria.descricao}</p>
                    </div>
                </div>
            </section>

            <section id="catalogo-categoria" className="category-catalogo">
                <div className="category-catalogo-container">
                    <ProductGrid
                        products={products}
                        loading={loading}
                        error={error}
                        retry={retry}
                        classeGrade="category-catalogo-grid"
                        mensagemCarregando="Carregando jogos..."
                        vazio={
                            <div className="category-empty">
                                <p>Ainda não temos jogos nessa categoria.</p>
                                <Link to="/#catalogo" className={`category-empty-button category-empty-button-${classe}`}>
                                    Ver catálogo completo
                                </Link>
                            </div>
                        }
                    />
                    {pageInfo && !error && (
                        <Pagination paginaAtual={pagina} totalPaginas={pageInfo.totalPages} onChange={trocarPagina} />
                    )}
                </div>
            </section>

            <Footer />
        </div>
    );
}
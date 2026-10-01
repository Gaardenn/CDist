import { useParams, Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { GameCard } from "../components/GameCard";
import { useProducts } from "../hooks/useProducts";
import "../styles/CategoryPage.css";
import { Loader } from "../components/Loader";
import { ErrorState } from "../components/ErrorState";

const CATEGORIAS = {
    playstation: {
        titulo: "PlayStation",
        subtitulo: "Console Sony, do PS1 ao PS5",
        logo: "/Img-Sony-PlayStation-PNG-Image 1.webp",
        classe: "playstation",
        filtro: (p) => p.plataforma.toLowerCase().includes("playstation") && !(p.tags || []).includes("Retro"),
    },
    xbox: {
        titulo: "Xbox",
        subtitulo: "Console Microsoft, do 360 ao Series X",
        logo: "/Img-Xbox.webp",
        classe: "xbox",
        filtro: (p) => p.plataforma.toLowerCase().includes("xbox"),
    },
    nintendo: {
        titulo: "Nintendo",
        subtitulo: "Switch, Wii U e a nostalgia Nintendo",
        logo: "/5bbc07b9af4bb-5135506e4e5927d3c9fc6b95a3322b97 1.webp",
        classe: "nintendo",
        filtro: (p) => p.plataforma.toLowerCase().includes("nintendo") && !(p.tags || []).includes("Retro"),
    },
    retro: {
        titulo: "Retro",
        subtitulo: "Clássicos que marcaram época",
        logo: "/563-5632966_sega-konsolen-liste-sega-mega-drive-classic-hd-Photoroom 1.webp",
        classe: "retro",
        filtro: (p) => (p.tags || []).includes("Retro"),
    },
};

export function CategoryPage({ quantTotal, cart }) {
    const { slug } = useParams();
    const { products, loading, error, retry } = useProducts();
    const categoria = CATEGORIAS[slug];

    if (loading) {
        return (
            <div className="page">
                <Navbar quantTotal={quantTotal} cart={cart} />
                <Loader mensagem="Carregando categoria..." />
                <Footer />
            </div>
        );
    }

    if (error) {
        return (
            <div className="page">
                <Navbar quantTotal={quantTotal} cart={cart} />
                <ErrorState mensagem={error} onRetry={retry} />
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

    const jogosFiltrados = products.filter(categoria.filtro);

    return (
        <div className="page">
            <Navbar quantTotal={quantTotal} cart={cart} />

            <section className={`category-banner category-banner-${categoria.classe}`}>
                <div className="category-banner-content">
                    <img src={categoria.logo} alt={categoria.titulo} className="category-banner-logo" />
                    <div className="category-banner-text">
                        <h1 className="category-banner-title">{categoria.titulo}</h1>
                        <p className="category-banner-subtitle">{categoria.subtitulo}</p>
                    </div>
                </div>
            </section>

            <section className="category-catalogo">
                <div className="category-catalogo-container">
                    {jogosFiltrados.length > 0 ? (
                        <div className="category-catalogo-grid">
                            {jogosFiltrados.map((p) => (
                                <GameCard key={p.id} jogo={p} />
                            ))}
                        </div>
                    ) : (
                        <div className="category-empty">
                            <p>Ainda não temos jogos nessa categoria.</p>
                            <Link to="/#catalogo" className={`category-empty-button category-empty-button-${categoria.classe}`}>
                                Ver catálogo completo
                            </Link>
                        </div>
                    )}
                </div>
            </section>

            <Footer />
        </div>
    );
}
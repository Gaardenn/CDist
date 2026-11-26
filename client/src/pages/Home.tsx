import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { GameCard } from "../components/GameCard";
import "../styles/Home.css";
import { UltimosAcessados } from "../components/UltimosAcessados";
import { useRef } from "react";
import { Loader } from "../components/Loader";
import { ErrorState } from "../components/ErrorState";
import { ProductGrid } from "../components/ProductGrid";
import { Pagination } from "../components/Pagination";
import { useProducts } from "../hooks/useProducts";
import { usePaginaUrl } from "../hooks/usePaginaUrl";
import type { CartItem } from "@/commons/types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft, faArrowRight } from "@fortawesome/free-solid-svg-icons";

type HomeProps = {
    quantTotal: () => number;
    cart: CartItem[];
    queue: number[];
};

export function Home({ quantTotal, cart, queue }: HomeProps) {
    const carrosselRef = useRef<HTMLDivElement>(null);
    const { pagina, irParaPagina } = usePaginaUrl();
    const destaques = useProducts({ size: 8 });
    const catalogo = useProducts({ page: pagina, size: 12 });

    function rolarCarrossel(direcao: "esquerda" | "direita"): void {
        if (!carrosselRef.current) return;
        const largura = carrosselRef.current.clientWidth;
        carrosselRef.current.scrollBy({
            left: direcao === "esquerda" ? -largura * 0.8 : largura * 0.8,
            behavior: "smooth"
        });
    }

    function trocarPagina(novaPagina: number): void {
        irParaPagina(novaPagina);
        document.getElementById("catalogo")?.scrollIntoView({ behavior: "smooth" });
    }

    return (
        <div className="page">
            <Navbar quantTotal={quantTotal} cart={cart} />

            <div className="page-body">
                <section className="page-body-hero">
                    <div className="page-body-hero-info">
                        <h1 className="page-body-hero-title">Mídias físicas e digitais de jogos novos e usados a <span className="page-body-hero-title-alt">um clique</span> de distância</h1>
                        <a href="#catalogo" className="page-body-hero-button">Confira nosso catálogo</a>
                    </div>
                    <img src="CD.webp" className="page-body-hero-image" />
                </section>
                <section className="page-body-classes">
                    <h2 className="page-body-classes-title">CATEGORIAS</h2>
                    <div className="page-body-classes-cards">
                        <div className="page-body-classes-playstation">
                            <Link to="/categoria/playstation" className="page-body-classes-playstation-gradient">
                                <img src="Img-Sony-PlayStation-PNG-Image 1.webp" alt="" className="page-body-classes-playstation-gradient-img" />
                                <h3 className="page-body-classes-playstation-gradient-title">PlayStation</h3>
                            </Link>
                        </div>
                        <div className="page-body-classes-xbox">
                            <Link to="/categoria/xbox" className="page-body-classes-xbox-gradient">
                                <img src="Img-Xbox.webp" alt="" className="page-body-classes-xbox-gradient-img" />
                                <h3 className="page-body-classes-xbox-gradient-title">Xbox</h3>
                            </Link>
                        </div>
                        <div className="page-body-classes-nintendo">
                            <Link to="/categoria/nintendo" className="page-body-classes-nintendo-gradient">
                                <img src="5bbc07b9af4bb-5135506e4e5927d3c9fc6b95a3322b97 1.webp" alt="" className="page-body-classes-nintendo-gradient-img" />
                                <h3 className="page-body-classes-nintendo-gradient-title">Nintendo</h3>
                            </Link>
                        </div>
                        <div className="page-body-classes-retro">
                            <Link to="/categoria/sega" className="page-body-classes-retro-gradient">
                                <img src="563-5632966_sega-konsolen-liste-sega-mega-drive-classic-hd-Photoroom 1.webp" alt="" className="page-body-classes-retro-gradient-img" />
                                <h3 className="page-body-classes-retro-gradient-title">Sega</h3>
                            </Link>
                        </div>
                    </div>
                </section>

                <section className="page-body-destaques">
                    <h2 className="page-body-destaques-title">PRODUTOS EM DESTAQUE</h2>
                    {destaques.loading && <Loader mensagem="Carregando destaques..." />}
                    {destaques.error && <ErrorState mensagem={destaques.error} onRetry={destaques.retry} />}
                    {!destaques.loading && !destaques.error && destaques.products.length > 0 && (
                        <div className="page-body-destaques-container">
                            <button className="destaques-arrow left" onClick={() => rolarCarrossel("esquerda")}><FontAwesomeIcon icon={faArrowLeft} /></button>

                            <div className="page-body-destaques-carrossel" ref={carrosselRef}>
                                {destaques.products.map((p) => (
                                    <GameCard key={p.id} jogo={p} />
                                ))}
                            </div>

                            <button className="destaques-arrow right" onClick={() => rolarCarrossel("direita")}><FontAwesomeIcon icon={faArrowRight} /></button>
                        </div>
                    )}
                </section>
                <section className="page-body-banner">
                    <img src="Banner.webp" className="banner-img" />
                    <a href="#catalogo" className="banner-button">VER TITULOS EM PROMOÇÃO</a>
                </section>

                <section className="page-body-ultimos-acessados">
                    <UltimosAcessados queue={queue} />
                </section>

                <section id="catalogo" className="page-body-catalogo">
                    <h2 className="page-body-catalogo-title">CATÁLOGO</h2>

                    <div className="page-body-catalogo-container">
                        <ProductGrid
                            products={catalogo.products}
                            loading={catalogo.loading}
                            error={catalogo.error}
                            retry={catalogo.retry}
                            classeGrade="page-body-catalogo-grid"
                            mensagemCarregando="Carregando catálogo..."
                            vazio={<p className="loader-mensagem">Nenhum produto disponível no momento.</p>}
                        />
                        {catalogo.pageInfo && !catalogo.error && (
                            <Pagination paginaAtual={pagina} totalPaginas={catalogo.pageInfo.totalPages} onChange={trocarPagina} />
                        )}
                    </div>
                </section>
            </div>

            <Footer />
        </div>
    );
}
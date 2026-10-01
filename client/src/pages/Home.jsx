import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import { GameCard } from "../components/GameCard";
import "../styles/Home.css";
import { UltimosAcessados } from "../components/UltimosAcessados";
import { useRef } from "react";
import { Loader } from "../components/Loader";
import { ErrorState } from "../components/ErrorState";

export function Home({ quantTotal, cart, queue, products, loading, error, retry }) {
    const carrosselRef = useRef(null);

    function rolarCarrossel(direcao) {
        if (!carrosselRef.current) return;
        const largura = carrosselRef.current.clientWidth;
        carrosselRef.current.scrollBy({
            left: direcao === "esquerda" ? -largura * 0.8 : largura * 0.8,
            behavior: "smooth"
        });
    }

    return (
        <div className="page">
            <Navbar quantTotal={quantTotal} cart={cart} />

            {loading && <Loader mensagem="Carregando catálogo..." />}
            {error && <ErrorState mensagem={error} onRetry={retry} />}

            {!loading && !error && (
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
                                <Link to="/categoria/retro" className="page-body-classes-retro-gradient">
                                    <img src="563-5632966_sega-konsolen-liste-sega-mega-drive-classic-hd-Photoroom 1.webp" alt="" className="page-body-classes-retro-gradient-img" />
                                    <h3 className="page-body-classes-retro-gradient-title">Retro</h3>
                                </Link>
                            </div>
                        </div>
                    </section>

                    <section className="page-body-destaques">
                        <h2 className="page-body-destaques-title">PRODUTOS EM DESTAQUE</h2>
                        <div className="page-body-destaques-container">
                            <button className="destaques-arrow left" onClick={() => rolarCarrossel("esquerda")}>‹</button>

                            <div className="page-body-destaques-carrossel" ref={carrosselRef}>
                                {products.map((p) => (
                                    <GameCard key={p.id} jogo={p} />
                                ))}
                            </div>

                            <button className="destaques-arrow right" onClick={() => rolarCarrossel("direita")}>›</button>
                        </div>
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
                            <div className="page-body-catalogo-grid">
                                {products.map((p) => (
                                    <GameCard
                                        key={"catalog-" + p.id}
                                        jogo={p}
                                    />
                                ))}
                            </div>
                        </div>
                    </section>

                </div>
            )}

            <Footer />
        </div>
    );
}
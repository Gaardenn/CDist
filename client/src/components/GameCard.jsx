import { Link } from "react-router-dom";
import "../styles/GameCard.css";

export function GameCard({ jogo = {} }) {
    const precoFinal = (jogo.preco || 0) - ((jogo.preco || 0) * ((jogo.desconto || 0) / 100));

    const imagemSrc = jogo.imagem?.startsWith("/") ? jogo.imagem : `/${jogo.imagem || ""}`;
    let plataformaClass = "default";
    const plataformaLower = (jogo.plataforma || "").toLowerCase();
    if (plataformaLower.includes("playstation")) plataformaClass = "playstation";
    if (plataformaLower.includes("xbox")) plataformaClass = "xbox";
    if (plataformaLower.includes("nintendo")) plataformaClass = "nintendo";
    if (plataformaLower.includes("mega drive")) plataformaClass = "retro";

    return (
        <div className="game-card">
            <Link to={`/produto/${jogo.id}`} className="link">
                <div className={`game-card-header ${plataformaClass}`}>
                    {jogo.plataforma}
                </div>
                <div className="game-card-image-container">
                    <img src={imagemSrc} alt={jogo.nome} className="game-card-image" loading="lazy" />
                </div>
                <div className="game-card-info">
                    <div className="game-card-platform-badge">{(jogo.tags && jogo.tags[0]) || jogo.plataforma}</div>
                    <h4 className="game-card-title">{jogo.nome}</h4>

                    <div className="game-card-pricing">
                        <div className="game-card-discount-info">
                            <span className="game-card-original-price">
                                A partir de {(jogo.desconto || 0) > 0 && <strike>R$ {(jogo.preco || 0).toFixed(2).replace('.', ',')}</strike>}
                            </span>
                            {(jogo.desconto || 0) > 0 && (
                                <span className="game-card-discount-badge">-{jogo.desconto}%</span>
                            )}
                        </div>
                        <div className="game-card-final-price">
                            R$ {precoFinal.toFixed(2).replace('.', ',')}
                        </div>
                    </div>
                </div>
            </Link>
        </div>
    );
}
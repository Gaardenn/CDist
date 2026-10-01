import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight, faDolly, faShieldHalved } from '@fortawesome/free-solid-svg-icons';
import '../styles/ProductDetails.css';

export function ProductDetails({produto}) {
    const { imagem, nome, dev, pub, midia, classificacao, data, modo, tags, descricao } = produto;

    return (
        <div className="product-details-container">
            <div className="product-gallery-panel">
                <div className="main-image-frame">
                    <img src={`/${imagem}`} alt="Capa do jogo God of War Ragnarök" className="main-image" />
                </div>

                <div className="thumbnail-gallery">
                    <button type="button" className="nav-arrow" aria-label="Imagem anterior">
                        <FontAwesomeIcon icon={faChevronLeft} />
                    </button>
                    <img src={`/${produto.imagem}`} alt="Miniatura 1" className="thumb thumb-active" />
                    <img src="/Jogo 2.webp" alt="Miniatura 2" className="thumb thumb-muted" />
                    <img src="/Jogo 3.webp" alt="Miniatura 3" className="thumb thumb-muted" />
                    <img src="/Jogo 4.webp" alt="Miniatura 4" className="thumb thumb-muted" />

                    <div className="thumb more-photos-circle">+5</div>
                    <button type="button" className="nav-arrow" aria-label="Próxima imagem">
                        <FontAwesomeIcon icon={faChevronRight} />
                    </button>
                </div>
            </div>

            <div className="product-info-section">
                <h1 className="product-title">{nome}</h1>
                <p className="info-line"><strong>Developer:</strong> {dev}</p>
                <p className="info-line"><strong>Publisher:</strong> {pub}</p>
                <p className="info-line"><strong>Mídia:</strong> {midia}</p>

                <h2 className="section-title">Descrição</h2>
                <p className="description-text">
                    {descricao}
                </p>

                <h2 className="section-title">Especificações</h2>
                <ul className="specs-list">
                    <li><span>Classificação Indicativa:</span> {classificacao} anos</li>
                    <li><span>Data de lançamento:</span> {data}</li>
                    <li><span>Modo de jogo:</span> {modo}</li>
                </ul>

                <h2 className="section-title">Tags</h2>
                <div className="tags-container">
                    {tags.map((t)=>(<span key={t} className="tag-category">{t}</span>))}
                </div>

                <div className="guarantees-container">
                    <div className="guarantee-item">
                        <FontAwesomeIcon icon={faShieldHalved} className="guarantee-icon" />
                        <span>Garantia estendida</span>
                    </div>
                    <div className="guarantee-item">
                        <FontAwesomeIcon icon={faDolly} className="guarantee-icon" />
                        <span>Devolução em até 7 dias</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
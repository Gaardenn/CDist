import '../styles/Navbar.css';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass, faCartShopping, faUser, faXmark } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import { useProducts } from '../hooks/useProducts';

export default function Navbar({ quantTotal, cart }) {
    const [quant, setQuant] = useState(() => quantTotal());
    const [termo, setTermo] = useState("");
    const { products } = useProducts();
    const navigate = useNavigate();

    useEffect(() => {
        setQuant(quantTotal())
    }, [cart]);

    const termoLimpo = termo.trim().toLowerCase();

    const resultados = termoLimpo === ""
        ? []
        : products.filter((p) =>
            p.nome.toLowerCase().includes(termoLimpo) ||
            p.plataforma?.toLowerCase().includes(termoLimpo) ||
            p.tags?.some(t => t.toLowerCase().includes(termoLimpo))
        ).slice(0, 6);

    function irParaProduto(id) {
        setTermo("");
        navigate(`/produto/${id}`);
    }

    return (
        <nav className="navbar">
            <Link to="/" className="navbar-brand">
                <img src="/cdlogo.svg" alt="CDist Logo" className="navbar-logo-img" />
                <div className="navbar-titles">
                    <h1 className="navbar-name">CDist</h1>
                    <span className="navbar-slogan">Sua locadora digital</span>
                </div>
            </Link>

            <div className="navbar-search-wrapper">
                <div className={`navbar-search ${termoLimpo !== "" ? "navbar-search-active" : ""}`}>
                    <span className="navbar-search-icon"><FontAwesomeIcon icon={faMagnifyingGlass} /></span>
                    <input
                        type="text"
                        placeholder="Pesquisar..."
                        className="navbar-search-input"
                        value={termo}
                        onChange={(e) => setTermo(e.target.value)}
                    />
                    {termo !== "" && (
                        <button className="navbar-search-clear" onClick={() => setTermo("")}>
                            <FontAwesomeIcon icon={faXmark} />
                        </button>
                    )}
                </div>

                {termoLimpo !== "" && (
                    <div className="navbar-search-drawer">
                        {resultados.length > 0 ? (
                            resultados.map((p) => (
                                <div
                                    key={p.id}
                                    className="navbar-search-item"
                                    onClick={() => irParaProduto(p.id)}
                                >
                                    <img src={`/${p.imagem}`} alt={p.nome} className="navbar-search-item-img" />
                                    <div className="navbar-search-item-info">
                                        <span className="navbar-search-item-nome">{p.nome}</span>
                                        <span className="navbar-search-item-plataforma">{p.plataforma}</span>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="navbar-search-empty">Nenhum jogo encontrado para "{termo}"</div>
                        )}
                    </div>
                )}
            </div>

            <div className="navbar-actions">
                <Link to="/carrinho" className="navbar-cart">
                    <span className="navbar-cart-icon"><FontAwesomeIcon icon={faCartShopping} /></span>
                    <span className="navbar-cart-counter">{quant}</span>
                </Link>

                <Link to="/autenticacao" className="navbar-login">
                    <span className="navbar-login-icon"><FontAwesomeIcon icon={faUser} /></span>
                    <span className="navbar-login-text">Login</span>
                </Link>
            </div>
        </nav>
    )
}
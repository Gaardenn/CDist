import React from "react"
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCreditCard, faBarcode } from "@fortawesome/free-solid-svg-icons";
import { faPix } from "@fortawesome/free-brands-svg-icons";
import "../styles/Footer.css"

function Footer() {
    return (
        <footer className="footer">
            <section className="footer-upper-divider" />
            <section>
                <div className="footer-body-info">
                    <div className="footer-body-info-column">
                        <h1 className="footer-body-info-column-header">Sobre nós</h1>
                        <p className="footer-body-info-column-text">A <span className="footer-body-info-column-text medium">CDist</span> é uma loja online
                            especializada em jogos em mídia física, reunindo clássicos retrô e títulos atuais para colecionadores e fãs de videogame.</p>
                    </div>
                    <div className="footer-body-info-column">
                        <h1 className="footer-body-info-column-header">Formas de pagamento</h1>
                        <ul className="footer-body-info-column-list">
                            <li className="footer-body-info-column-list-item">
                                <FontAwesomeIcon icon={faCreditCard} className="footer-body-info-column-list-item-icon" />
                                <p className="footer-body-info-column-list-text">Cartão de crédito</p> 
                            </li>
                            <li className="footer-body-info-column-list-item">
                                <FontAwesomeIcon icon={faBarcode} className="footer-body-info-column-list-item-icon" />
                                <p className="footer-body-info-column-list-text">Boleto</p> 
                            </li>
                            <li className="footer-body-info-column-list-item">
                                <FontAwesomeIcon icon={faPix} className="footer-body-info-column-list-item-icon" />
                                <p className="footer-body-info-column-list-text">Pix</p> 
                            </li>
                        </ul>
                    </div>
                    <div className="footer-body-info-column">
                        <h1 className="footer-body-info-column-header">Atendimento</h1>
                        <ul className="footer-body-info-column-list">
                            <li><a href="mailto::contato@cdist.com" className="footer-body-info-column-list-link">contato@cdist.com</a></li>
                            <li className="footer-body-info-column-list-text">(46) 99999-9999</li>
                            <li className="footer-body-info-column-list-text">Seg a Sex, das 8h às 18h</li>
                        </ul>
                    </div>
                    <div className="footer-body-info-column">
                        <h1 className="footer-body-info-column-header">Informações</h1>
                        <ul className="footer-body-info-column-list">
                            <Link to="/informacoes" className="footer-body-info-column-list-text">Trocas e devoluções</Link>
                            <Link to="/informacoes" className="footer-body-info-column-list-text">Frete e entrega</Link>
                            <Link to="/informacoes" className="footer-body-info-column-list-text">FAQ</Link>
                            <Link to="/informacoes" className="footer-body-info-column-list-text">Termos de Uso</Link>
                            <Link to="/informacoes" className="footer-body-info-column-list-text">Política de Privacidade</Link>
                        </ul>
                    </div>
                </div>
            </section>
            <section className="footer-lower-divider" />
            <section className="footer-end">
                <div className="footer-end-content">
                    <div>
                        <Link to="/" className="footer-end-content-link">
                            <img src="/cdlogo.svg" alt="" className="footer-end-logo-img" />
                            <h1 className="footer-end-content-name">CDist</h1>
                        </Link>
                    </div>
                    <div>
                        <p className="footer-end-content-dot">•</p>
                    </div>
                    <div>
                        <p className="footer-end-content-copyright">© 2026. Todos os direitos reservados</p>
                    </div>
                </div>
            </section>
        </footer>
    )
}

export default Footer;
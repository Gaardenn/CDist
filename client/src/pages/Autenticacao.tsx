import AuthForm from "../components/AuthForm";
import { Link } from "react-router-dom";
import "../styles/Autenticacao.css";

export function Autenticacao() {
    return (
        <div className="auth-page">
            <div className='auth-header'>
                <Link to="/" className="auth-brand">
                    <img src="/cdlogo.svg" alt="CDist Logo" className="auth-logo-img"/>
                    <div className="auth-titles">
                        <h2>CDist</h2>
                        <span>Sua locadora digital</span>
                    </div>
                </Link>
            </div>

            <AuthForm />

            <section>
                <section className="footer-divider" />
                <div className="auth-footer-info">
                    <img src="/cdlogo.svg" alt="CDist Logo" className="auth-footer-logo-img"/>
                    <span className="auth-footer-title">CDist</span>
                    <span className="auth-footer-point">•</span>
                    <span className="auth-footer-text">© 2026. Todos os direitos reservados.</span>
                </div>
            </section>
                
        </div>
    );
}
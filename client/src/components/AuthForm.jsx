import { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import { useToast } from "../context/ToastContext";
import "../styles/Autenticacao.css";

export default function AuthForm({ initialMode = "login" }) {
    const [mode, setMode] = useState(initialMode);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [erro, setErro] = useState("");
    const { showToast } = useToast();
    const navigate = useNavigate();

    const isLogin = mode === "login";

    function trocarModo(novoModo) {
        setErro("");
        setMode(novoModo);
    }

    function handleSubmit(e) {
        e.preventDefault();

        if (email === "") {
            setErro("Preencha o e-mail.");
            return;
        }
        if (!email.includes("@")) {
            setErro("E-mail inválido.");
            return;
        }
        if (password === "") {
            setErro("Preencha a senha.");
            return;
        }

        if (!isLogin) {
            if (confirmPassword === "") {
                setErro("Confirme a senha.");
                return;
            }
            if (password !== confirmPassword) {
                setErro("As senhas não coincidem.");
                return;
            }
        }

        setErro("");
        localStorage.setItem("email", email);
        localStorage.setItem("password", password);
        showToast(isLogin ? "Login realizado com sucesso!" : "Conta criada com sucesso!", "sucesso");
        navigate("/");
    }

    return (
        <div className="auth-container">
            <h1 key={mode + "-title"} className="auth-title auth-fade">
                {isLogin ? "Fazer login" : "Criar conta"}
            </h1>

            <form className="auth-form" onSubmit={handleSubmit}>
                <label className="auth-field">
                    <span>Insira seu endereço de e-mail</span>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                </label>

                <label className="auth-field">
                    <span>Senha</span>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </label>

                <div className={`auth-extra-field ${!isLogin ? "auth-extra-field-open" : ""}`}>
                    <div className="auth-extra-field-inner">
                        <label className="auth-field">
                            <span>Confirmar senha</span>
                            <input
                                type="password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                            />
                        </label>
                    </div>
                </div>

                {erro && <p className="auth-error">{erro}</p>}

                <button type="submit" className="auth-submit-button">
                    {isLogin ? "Fazer login" : "Criar conta"}
                </button>

                <p className="auth-terms">
                    Ao continuar, você concorda com os <Link to="/informacoes">Termos de Uso</Link> e <Link to="/informacoes">Política de Privacidade</Link>.
                </p>

                {isLogin && <p key="signup-text" className="auth-signup-text auth-fade">Não possui cadastro?</p>}

                <button
                    type="button"
                    className="auth-create-account-button"
                    onClick={() => trocarModo(isLogin ? "signup" : "login")}
                >
                    <span key={mode + "-btn"} className="auth-fade">
                        {isLogin ? "Criar conta" : "Fazer login"}
                    </span>
                </button>
            </form>
        </div>
    );
}
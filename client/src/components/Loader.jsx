import "../styles/Loader.css";

export function Loader({ mensagem = "Carregando..." }) {
    return (
        <div className="loader-container">
            <div className="loader-spinner"></div>
            <p className="loader-mensagem">{mensagem}</p>
        </div>
    );
}
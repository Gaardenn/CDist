import "../styles/Loader.css";

type LoaderProps = {
    mensagem?: string;
}

export function Loader({ mensagem = "Carregando..." }: LoaderProps) {
    return (
        <div className="loader-container">
            <div className="loader-spinner"></div>
            <p className="loader-mensagem">{mensagem}</p>
        </div>
    );
}
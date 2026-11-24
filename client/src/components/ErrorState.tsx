import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../styles/Loader.css";
import { faExclamation } from "@fortawesome/free-solid-svg-icons";

type ErrorStateProps = {
    mensagem?: string;
    onRetry?: () => void;
}

export function ErrorState({ mensagem = "Algo deu errado.", onRetry }: ErrorStateProps) {
    return (
        <div className="loader-container">
            <div className="error-icon"><FontAwesomeIcon icon={faExclamation} /></div>
            <p className="loader-mensagem">{mensagem}</p>
            {onRetry && (
                <button className="error-retry-button" onClick={onRetry}>
                    Tentar novamente
                </button>
            )}
        </div>
    );
}
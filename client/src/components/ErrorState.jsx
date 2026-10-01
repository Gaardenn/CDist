import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../styles/Loader.css";
import { faExclamation } from "@fortawesome/free-solid-svg-icons";

export function ErrorState({ mensagem = "Algo deu errado.", onRetry }) {
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
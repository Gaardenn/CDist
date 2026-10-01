import { faCheck, faExclamation, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { createContext, useCallback, useContext, useState } from "react";

const ToastContext = createContext(null);

let idCounter = 0;

export function ToastProvider({ children }) {
    const [toasts, setToasts] = useState([]);

    const removeToast = useCallback((id) => {
        setToasts((atual) => atual.filter((t) => t.id !== id));
    }, []);

    const showToast = useCallback((mensagem, tipo = "sucesso") => {
        const id = ++idCounter;
        setToasts((atual) => [...atual, { id, mensagem, tipo }]);

        setTimeout(() => removeToast(id), 3500);
    }, [removeToast]);

    return (
        <ToastContext.Provider value={{ showToast }}>
            {children}
            <div className="toast-container">
                {toasts.map((t) => (
                    <div key={t.id} className={`toast toast-${t.tipo}`}>
                        <span className="toast-icon">
                            {t.tipo === "sucesso" ? <FontAwesomeIcon icon={faCheck} /> : t.tipo === "erro" ? <FontAwesomeIcon icon={faXmark} /> : <FontAwesomeIcon icon={faExclamation} />}
                        </span>
                        <span className="toast-mensagem">{t.mensagem}</span>
                        <button className="toast-close" onClick={() => removeToast(t.id)}><FontAwesomeIcon icon={faXmark} /></button>
                    </div>
                ))}
            </div>
        </ToastContext.Provider>
    );
}

export function useToast() {
    const context = useContext(ToastContext);
    if (!context) {
        throw new Error("useToast precisa ser usado dentro de um ToastProvider");
    }
    return context;
}
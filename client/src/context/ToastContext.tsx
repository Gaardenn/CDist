import { faCheck, faExclamation, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { createContext, useCallback, useContext, useState, type ReactNode } from "react";

interface Toast {
    id: number;
    mensagem: string;
    tipo: "sucesso" | "erro" | "aviso";
}

interface ToastContextType {
    showToast: (mensagem: string, tipo?: Toast["tipo"]) => void;
}

interface ToastProviderProps {
    children: ReactNode;
}

const ToastContext = createContext<ToastContextType | null>(null);

let idCounter = 0;

export function ToastProvider({ children }: ToastProviderProps) {
    const [toasts, setToasts] = useState<Toast[]>([]);

    const removeToast = useCallback((id: number) => {
        setToasts((atual) => atual.filter((t) => t.id !== id));
    }, []);

    const showToast = useCallback((mensagem: string, tipo: Toast["tipo"] = "sucesso") => {
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

export function useToast(): ToastContextType {
    const context = useContext(ToastContext);
    if (!context) {
        throw new Error("useToast precisa ser usado dentro de um ToastProvider");
    }
    return context;
}
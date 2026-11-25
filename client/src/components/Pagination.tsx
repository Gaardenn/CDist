import { faArrowLeft, faArrowRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../styles/Pagination.css";

type PaginationProps = {
    paginaAtual: number; // começa em 0 igual a API
    totalPaginas: number;
    onChange: (pagina: number) => void;
};

function gerarPaginas(atual: number, total: number): (number | "...")[] {
    const paginas: (number | "...")[] = [];
    for (let i = 0; i < total; i++) {
        if (i === 0 || i === total - 1 || Math.abs(i - atual) <= 1) {
            paginas.push(i);
        } else if (paginas[paginas.length - 1] !== "...") {
            paginas.push("...");
        }
    }
    return paginas;
}

export function Pagination({ paginaAtual, totalPaginas, onChange }: PaginationProps) {
    if (totalPaginas <= 1) return null;

    return (
        <nav className="pagination" aria-label="Paginação">
            <button className="pagination-button" disabled={paginaAtual === 0}
                onClick={() => onChange(paginaAtual - 1)}>
                <FontAwesomeIcon icon={faArrowLeft} /> Anterior
            </button>

            {gerarPaginas(paginaAtual, totalPaginas).map((item, i) =>
                item === "..." ? (
                    <span key={`reticencias-${i}`} className="pagination-ellipsis">...</span>
                ) : (
                    <button key={item}
                        className={`pagination-button ${item === paginaAtual ? "pagination-button-active" : ""}`}
                        aria-current={item === paginaAtual ? "page" : undefined}
                        onClick={() => onChange(item)}>
                        {item + 1}
                    </button>
                )
            )}

            <button className="pagination-button" disabled={paginaAtual >= totalPaginas - 1}
                onClick={() => onChange(paginaAtual + 1)}>
                Próxima <FontAwesomeIcon icon={faArrowRight} />
            </button>
        </nav>
    );
}
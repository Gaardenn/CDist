import { Link } from "react-router-dom";
import { formatarDataHora } from "../utils/formatarData";
import { formatarPreco } from "../utils/formatarPreco";
import { METODO_PAGAMENTO, STATUS_PEDIDO } from "../utils/pedidoLabels";
import type { PedidoResponseDTO } from "@/commons/types";

type PedidoCardProps = {
    pedido: PedidoResponseDTO;
}

export function PedidoCard({ pedido }: PedidoCardProps) {
    const { id, data, status, metodoPagamento, itens, frete, total } = pedido;

    return (
        <article className="pedido-card">
            <header className="pedido-card-header">
                <div>
                    <h2 className="pedido-card-numero">Pedido #{id}</h2>
                    <p className="pedido-card-data">{formatarDataHora(data)}</p>
                </div>
                <span className={`pedido-status pedido-status-${status.toLowerCase().replaceAll("_", "-")}`}>
                    {STATUS_PEDIDO[status] ?? status}
                </span>
            </header>

            <ul className="pedido-card-itens">
                {itens.map((item) => (
                    <li key={item.produtoId} className="pedido-item">
                        <img src={item.imagem} alt={item.nome} className="pedido-item-imagem" loading="lazy" />
                        <div className="pedido-item-info">
                            <Link to={`/produto/${item.produtoId}`} className="pedido-item-nome">{item.nome}</Link>
                            <p className="pedido-item-detalhe">
                                {item.quantidade} x R$ {formatarPreco(item.precoFinal)}
                                {item.desconto > 0 && (
                                    <span className="pedido-item-desconto"> (-{Math.round(item.desconto * 100)}%)</span>
                                )}
                            </p>
                        </div>
                        <p className="pedido-item-subtotal">R$ {formatarPreco(item.subtotal)}</p>
                    </li>
                ))}
            </ul>

            <footer className="pedido-card-footer">
                <p className="pedido-card-pagamento">Pagamento: {METODO_PAGAMENTO[metodoPagamento] ?? metodoPagamento}</p>
                <div className="pedido-card-totais">
                    <p className="pedido-card-frete">Frete: R$ {formatarPreco(frete)}</p>
                    <p className="pedido-card-total">Total: R$ {formatarPreco(total)}</p>
                </div>
            </footer>
        </article>
    );
}
import { useProducts } from '../hooks/useProducts';
import '../styles/UltimosAcessados.css';
import { GameCard } from './GameCard';

export function UltimosAcessados({ queue = [] }) {
    const {getProduct} = useProducts();

    const produtos = queue
        .slice()
        .reverse()
        .map((id) => getProduct(id))
        .filter(Boolean);

    if (produtos.length === 0) {
        return null;
    }

    return (
        <div className="ultimos-acessados-container">
            <h2 className="ultimos-acessados-title">ÚLTIMOS ACESSADOS</h2>
            <div className="ultimos-acessados-grid">
                {produtos.map((produto) => (
                    <GameCard key={produto.id} jogo={produto} />
                ))}
            </div>
        </div>
    );
}

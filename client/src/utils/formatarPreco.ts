export function formatarPreco(valor: number): string {
    return (valor || 0).toLocaleString("pt-BR", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    });
}
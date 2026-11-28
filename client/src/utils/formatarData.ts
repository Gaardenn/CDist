/** "2026-11-24T17:30:00" vira "24/11/2026 às 17:30" */
export function formatarDataHora(iso: string): string {
    const data = new Date(iso);
    if (Number.isNaN(data.getTime())) return iso;

    const dia = data.toLocaleDateString("pt-BR", { day: "2-digit", month: "2-digit", year: "numeric" });
    const hora = data.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" });
    return `${dia} às ${hora}`;
}
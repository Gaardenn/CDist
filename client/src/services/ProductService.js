export async function ProductService() {
    const response = await fetch("/jogos.json");
    
    if (!response.ok) {
        throw new Error("Não foi possível carregar os produtos");
    }
    
    return await response.json();
}
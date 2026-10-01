import { useState, useEffect } from "react"

export function useUltimosAcessados(){
    const [queue, setQueue] = useState(()=>{
        return JSON.parse(localStorage.getItem("fila")) || [];
    })

    useEffect(() => {
        localStorage.setItem("fila", JSON.stringify(queue));
    }, [queue]);

    const adicionarProdutoFila = (produto) => {
        const temp = queue.filter((id) => id !== produto.id);
        let newQueue = [...temp, produto.id];
        if (newQueue.length > 5) {
            newQueue = newQueue.slice(newQueue.length - 5);
        }
        setQueue(newQueue);
    }

    return {queue, adicionarProdutoFila};
}
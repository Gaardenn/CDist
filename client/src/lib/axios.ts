import axios from 'axios';

/** API principal (Porta 8080) */
export const apiPrincipal = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
});

/** API de logística (Porta 8081) */
export const apiLogistica = axios.create({
    baseURL: import.meta.env.VITE_LOGISTICA_URL,
});
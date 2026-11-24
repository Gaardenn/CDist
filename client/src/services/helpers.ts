import axios from "axios";
import type { AxiosResponse } from "axios";
import type { IApiError, IResponse } from "@/commons/types";

/** Executa chamada do axios e devolve um IResponse. */
export async function request<T> (
    call: () => Promise<AxiosResponse<T>>,
    okMessage: string,
    failMessage: string
): Promise<IResponse<T>> {
    try {
        const res = await call();
        return { status: res.status, success: true, message: okMessage, data: res.data };
    } catch (err) {
        return toErrorResponse<T>(err, failMessage);
    }
}

export function toErrorResponse<T>(err: unknown, failMessage: string): IResponse<T> {
    if (axios.isAxiosError<IApiError>(err)) {
        if (err.message) {
            return {
                status: err.response.status,
                success: false,
                message: failMessage,
                error: err.response.data,
            };
        }
        // sem resposta
        return {
            status: 0,
            success: false,
            message: "Servidor indisponível. Verifique sua conexão e tente novamente.",
        };
    }
    return { status: 0, success: false, message: failMessage };
}
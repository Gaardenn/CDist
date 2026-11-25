/** Formato padrão que os services retornam */
export interface IResponse<T = unknown> {
    status: number; // HTTP
    success: boolean;
    message: string; // texto para o toast exibir
    data?: T; // se for sucesso é preenchido
    error?: IApiError; // se for falha é preenchido
}

/** Corpo do erro da API */
export interface IApiError {
    timestamp?: number;
    status?: number;
    message?: string;
    url?: string;
    validationErrors?: Record<string, string>;
}

/** Page<T> do Spring */
export interface IPagedModel<T> {
    content: T[];
    page: {
        size: number;
        number: number; // página atual
        totalElements: number;
        totalPages: number;
    };
}
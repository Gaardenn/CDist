package com.ursominhoco.logistica.exception;

public class ServicoCepIndisponivelException extends RuntimeException {
    public ServicoCepIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public ServicoCepIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
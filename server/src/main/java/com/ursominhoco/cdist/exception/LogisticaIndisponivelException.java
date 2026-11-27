package com.ursominhoco.cdist.exception;

public class LogisticaIndisponivelException extends RuntimeException {
    public LogisticaIndisponivelException(String mensagem) {
        super(mensagem);
    }

    public LogisticaIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
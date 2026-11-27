package com.ursominhoco.cdist.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String produto, int disponivel, int solicitado) {
        super("Estoque insuficiente para \"" + produto + "\": disponível " + disponivel
                + ", solicitado " + solicitado);
    }
}
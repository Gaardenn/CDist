package com.ursominhoco.logistica.exception;

public class CepNaoEncontradoException extends RuntimeException {
    public CepNaoEncontradoException() {
        super("CEP não encontrado");
    }
}
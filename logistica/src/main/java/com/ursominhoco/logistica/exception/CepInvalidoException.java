package com.ursominhoco.logistica.exception;

public class CepInvalidoException extends RuntimeException {
    public CepInvalidoException() {
        super("CEP inválido. Informe 8 dígitos, com ou sem hífen.");
    }
}
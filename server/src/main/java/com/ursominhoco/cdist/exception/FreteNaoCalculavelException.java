package com.ursominhoco.cdist.exception;

public class FreteNaoCalculavelException extends RuntimeException {
    public FreteNaoCalculavelException() {
        super("Não foi possível calcular o frete: o CEP do endereço é inválido ou não foi encontrado");
    }
}
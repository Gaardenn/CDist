package com.ursominhoco.logistica.service;

import com.ursominhoco.logistica.exception.CepInvalidoException;

public final class CepUtil {

    private CepUtil() {
    }

    /** "85501-000" ou "85501000" vira "85501000". Todo o resto lança CepInvalidoException */
    public static String normalizar(String cep) {
        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new CepInvalidoException();
        }
        return cep.replace("-", "");
    }
}
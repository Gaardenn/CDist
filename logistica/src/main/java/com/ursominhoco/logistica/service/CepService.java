package com.ursominhoco.logistica.service;

import com.ursominhoco.logistica.client.ViaCepClient;
import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.exception.CepNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CepService {

    private final ViaCepClient viaCepClient;

    public CepResponse buscar(String cepInformado) {
        String cep = CepUtil.normalizar(cepInformado);
        return viaCepClient.buscar(cep).orElseThrow(CepNaoEncontradoException::new);
    }
}
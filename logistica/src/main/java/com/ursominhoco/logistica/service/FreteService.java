package com.ursominhoco.logistica.service;

import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.dto.CotacaoResponse;
import com.ursominhoco.logistica.exception.ServicoCepIndisponivelException;
import com.ursominhoco.logistica.frete.FreteCalculator;
import com.ursominhoco.logistica.frete.RegiaoFrete;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreteService {

    private final CepService cepService;

    public CotacaoResponse cotar(String cepDestino, int itensFisicos) {
        String cep = CepUtil.normalizar(cepDestino);

        // Se for só itens digitais o frete é zero sem precisar do ViaCEP
        if (itensFisicos == 0) {
            return FreteCalculator.calcular(null, 0);
        }

        CepResponse destino = cepService.buscar(cep);
        RegiaoFrete regiao = RegiaoFrete.porUf(destino.estado())
                .orElseThrow(() -> new ServicoCepIndisponivelException(
                        "Serviço de consulta de CEP devolveu uma UF desconhecida: " + destino.estado()));

        return FreteCalculator.calcular(regiao, itensFisicos);
    }
}
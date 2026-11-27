package com.ursominhoco.logistica.client;

import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.exception.ServicoCepIndisponivelException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCepClient {

    private final RestClient restClient;

    /** @param cep 8 dígitos que já vem validado */
    public Optional<CepResponse> buscar(String cep) {
        Map<String, Object> corpo;
        try {
            corpo = restClient.get()
                    .uri("/{cep}/json/", cep)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (RestClientException e) {
            log.warn("Falha ao consultar o ViaCEP para o CEP {}: {}", cep, e.getMessage());
            throw new ServicoCepIndisponivelException("Serviço de consulta de CEP indisponível", e);
        }

        if (corpo == null) {
            throw new ServicoCepIndisponivelException("Serviço de consulta de CEP devolveu resposta vazia");
        }

        // CEP bem formado mas inexistente: {"erro": true} ou {"erro": "true"}
        if ("true".equalsIgnoreCase(String.valueOf(corpo.get("erro")))) {
            return Optional.empty();
        }

        return Optional.of(new CepResponse(
                cep,
                texto(corpo, "logradouro"),
                texto(corpo, "bairro"),
                texto(corpo, "localidade"),
                texto(corpo, "uf")
        ));
    }

    private String texto(Map<String, Object> corpo, String campo) {
        Object valor = corpo.get(campo);
        return valor == null ? "" : valor.toString();
    }
}
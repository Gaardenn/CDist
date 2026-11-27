package com.ursominhoco.cdist.client;

import com.ursominhoco.cdist.dto.CotacaoFreteRequestDTO;
import com.ursominhoco.cdist.dto.CotacaoFreteResponseDTO;
import com.ursominhoco.cdist.exception.FreteNaoCalculavelException;
import com.ursominhoco.cdist.exception.LogisticaIndisponivelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
public class LogisticaClient {

    private final RestClient restClient;

    public LogisticaClient(@Qualifier("logisticaRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Pede o frete para a API de logística.
     *
     * @param cepDestino CEP do pedido (8 dígitos)
     * @param itensFisicos soma as quantidades dos itens de mídia física
     * @throws FreteNaoCalculavelException a logística recusou o CEP (440 ou 404)
     * @throws LogisticaINdisponivelException fora do ar, timeout, 5xx ou resposta inválida
     */
    public BigDecimal cotarFrete(String cepDestino, int itensFisicos) {
        CotacaoFreteResponseDTO resposta;
        try {
            resposta = restClient.post()
                    .uri("/frete/cotacao")
                    .body(new CotacaoFreteRequestDTO(cepDestino, itensFisicos))
                    .retrieve()
                    .body(CotacaoFreteResponseDTO.class);
        } catch (HttpClientErrorException e) {
            int status = e.getStatusCode().value();
            if (status == 400 || status == 404) {
                throw new FreteNaoCalculavelException();
            }
            log.warn("A logística recusou a cotação (HTTP {})", status);
            throw new LogisticaIndisponivelException("Serviço de logística indisponível", e);
        } catch (RestClientException e) {
            log.warn("Falha ao consultar a logística: {}", e.getMessage());
            throw new LogisticaIndisponivelException("Serviço de logística indisponível", e);
        }

        if (resposta == null || resposta.valor() == null) {
            throw new LogisticaIndisponivelException("Serviço de logística devolveu uma resposta inválida");
        }
        return resposta.valor().setScale(2, RoundingMode.HALF_UP);
    }
}
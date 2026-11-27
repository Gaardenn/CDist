package com.ursominhoco.cdist;

import com.ursominhoco.cdist.client.LogisticaClient;
import com.ursominhoco.cdist.exception.FreteNaoCalculavelException;
import com.ursominhoco.cdist.exception.LogisticaIndisponivelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class LogisticaClientTest {

    private static final String URL_COTACAO = "http://logistica.test/frete/cotacao";

    private MockRestServiceServer servidor;
    private LogisticaClient cliente;

    @BeforeEach
    void preparar() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://logistica.test");
        servidor = MockRestServiceServer.bindTo(builder).build();
        cliente = new LogisticaClient(builder.build());
    }

    @Test
    void cotarFrete_logisticaResponde_enviaCepEItensEDevolveOValor() {
        servidor.expect(requestTo(URL_COTACAO))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.cepDestino").value("85580001"))
                .andExpect(jsonPath("$.itensFisicos").value(2))
                .andRespond(withSuccess("""
                        {"valor": 15.00, "prazoDias": 3, "regiao": "Sul"}
                        """, MediaType.APPLICATION_JSON));

        BigDecimal frete = cliente.cotarFrete("85580001", 2);

        assertThat(frete).isEqualByComparingTo("15.00");
        servidor.verify();
    }

    @Test
    void cotarFrete_logisticaComErro502_lancaLogisticaIndisponivel() {
        servidor.expect(requestTo(URL_COTACAO))
                .andRespond(withStatus(HttpStatus.BAD_GATEWAY));

        assertThatThrownBy(() -> cliente.cotarFrete("85580001", 1))
                .isInstanceOf(LogisticaIndisponivelException.class);
    }

    @Test
    void cotarFrete_logisticaForaDoAr_lancaLogisticaIndisponivel() {
        servidor.expect(requestTo(URL_COTACAO))
                .andRespond(request -> {
                    throw new ConnectException("Connection refused");
                });

        assertThatThrownBy(() -> cliente.cotarFrete("85580001", 1))
                .isInstanceOf(LogisticaIndisponivelException.class);
    }

    @Test
    void cotarFrete_cepInexistente_lancaFreteNaoCalculavel() {
        servidor.expect(requestTo(URL_COTACAO))
                .andRespond(withResourceNotFound());

        assertThatThrownBy(() -> cliente.cotarFrete("99999999", 1))
                .isInstanceOf(FreteNaoCalculavelException.class);
    }
}
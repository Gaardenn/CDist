package com.ursominhoco.logistica;

import com.ursominhoco.logistica.client.ViaCepClient;
import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.exception.ServicoCepIndisponivelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.net.SocketTimeoutException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ViaCepClientTest {

    private MockRestServiceServer servidor;
    private ViaCepClient cliente;

    @BeforeEach
    void preparar() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://viacep.test/ws");
        servidor = MockRestServiceServer.bindTo(builder).build();
        cliente = new ViaCepClient(builder.build());
    }

    @Test
    void buscar_cepExistente_devolveEndereco() {
        servidor.expect(requestTo("http://viacep.test/ws/85501000/json/"))
                .andRespond(withSuccess("""
                        {"cep":"85501-000","logradouro":"Rua Caramuru","complemento":"","bairro":"Centro",
                         "localidade":"Pato Branco","uf":"PR","ibge":"4118501"}
                        """, MediaType.APPLICATION_JSON));

        Optional<CepResponse> resposta = cliente.buscar("85501000");

        assertThat(resposta).hasValueSatisfying(endereco -> {
            assertThat(endereco.cep()).isEqualTo("85501000");
            assertThat(endereco.logradouro()).isEqualTo("Rua Caramuru");
            assertThat(endereco.bairro()).isEqualTo("Centro");
            assertThat(endereco.cidade()).isEqualTo("Pato Branco");
            assertThat(endereco.estado()).isEqualTo("PR");
        });
        servidor.verify();
    }

    @Test
    void buscar_erroBooleando_devolveVazio() {
        servidor.expect(requestTo("http://viacep.test/ws/99999999/json/"))
                .andRespond(withSuccess("{\"erro\": true}", MediaType.APPLICATION_JSON));

        assertThat(cliente.buscar("99999999")).isEmpty();
    }

    @Test
    void buscar_erroComoTexto_devolveVazio() {
        servidor.expect(requestTo("http://viacep.test/ws/99999999/json/"))
                .andRespond(withSuccess("{\"erro\": \"true\"}", MediaType.APPLICATION_JSON));

        assertThat(cliente.buscar("99999999")).isEmpty();
    }

    @Test
    void buscar_viaCepComErro500_lancaServicoIndisponivel() {
        servidor.expect(requestTo("http://viacep.test/ws/85501000/json/"))
                .andRespond(withServerError());

        assertThatThrownBy(() -> cliente.buscar("85501000"))
                .isInstanceOf(ServicoCepIndisponivelException.class);
    }

    @Test
    void buscar_timeout_lancaServicoIndisponivel() {
        servidor.expect(requestTo("http://viacep.test/ws/85501000/json/"))
                .andRespond(request -> {
                    throw new SocketTimeoutException("Read timed out");
                });

        assertThatThrownBy(() -> cliente.buscar("85501000"))
                .isInstanceOf(ServicoCepIndisponivelException.class);
    }
}
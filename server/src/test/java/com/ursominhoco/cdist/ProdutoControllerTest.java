package com.ursominhoco.cdist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class ProdutoControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getProdutos_quandoSemToken_retornaOK() {
        ResponseEntity<Object> resposta = testRestTemplate.getForEntity("/produtos", Object.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

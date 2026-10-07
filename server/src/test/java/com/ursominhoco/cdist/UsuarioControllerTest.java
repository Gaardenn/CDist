package com.ursominhoco.cdist;

import com.ursominhoco.cdist.entity.Usuario;
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
public class UsuarioControllerTest {
    private static final String API_USUARIO = "/usuario";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void postUser_whenUserIsValid_receiveCREATED() {
        Usuario usuario = new Usuario();
        usuario.setNome("testeUsuario");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("Teste1235");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USUARIO, usuario, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
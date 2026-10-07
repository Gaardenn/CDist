package com.ursominhoco.cdist;

import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void limparBanco() {
        usuarioRepository.deleteAll();
    }

    @Test
    public void postUsuario_quandoUsuarioEValido_recebeCREATED() {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("Teste1235");

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USUARIO, usuario, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void postUsuario_quandoUsuarioTemNomeNulo_recebeBADREQUEST() {
        Usuario usuario = criarUsuarioValido();
        usuario.setNome(null);
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USUARIO, usuario, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUsuario_quandoUsuarioTemNomeMenorQueQuatroCaracteres_recebeBADREQUEST() {
        Usuario usuario = criarUsuarioValido();
        usuario.setNome("Tes");
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USUARIO, usuario, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUsuario_quandoUsuarioTemNomeMaiorQueCinquentaCaracteres_recebeBADREQUEST() {
        Usuario usuario = criarUsuarioValido();
        usuario.setNome("Teste Teste Teste Teste Teste Teste Teste Teste Tes");
        ResponseEntity<Object> resposta = testRestTemplate.postForEntity(API_USUARIO, usuario, Object.class);
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private Usuario criarUsuarioValido() {
        return Usuario.builder().nome("testeUsuario").email("teste@teste.com").senha("Teste1235").build();
    }
}
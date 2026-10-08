package com.ursominhoco.cdist;

import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class JWTAuthenticationFilterTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TestRestTemplate templateTesteRest;
    @Autowired
    private PasswordEncoder codificadorSenha;

    @BeforeEach
    public void configura() {
        usuarioRepository.deleteAll();
        usuarioRepository.save(Usuario.builder().email("teste@teste.com").nome("Teste Teste").senha(codificadorSenha
                .encode("Teste1235")).build());
    }

    @Test
    public void postUsuarios_quandoSenhaErrada_recebe401() {
        Map<String, String> credenciais = Map.of(
                "email", "teste@teste.com",
                "senha", "TesteErrado1"
        );

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoEmailNaoExiste_recebe401() {
        Map<String, String> credenciais = Map.of(
                "email", "testenaoexiste@teste.com",
                "senha", "Teste1"
        );

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoEmailNulo_recebe401() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", null);
        credenciais.put("senha", "Teste1235");

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoSenhaNula_recebe401() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", "teste@teste.com");
        credenciais.put("senha", null);

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoEmailVazio_recebe401() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", "");
        credenciais.put("senha", "Teste1235");

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoSenhaVazia_recebe401() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", "teste@teste.com");
        credenciais.put("senha", "");

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoJsonMalFormatado_recebe401() {
        String corpoInvalido = "{ nao e json";

        HttpHeaders cabecalhos = new HttpHeaders();
        cabecalhos.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requisicao = new HttpEntity<>(corpoInvalido, cabecalhos);

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", requisicao, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoCorpoVazio_recebe401() {
        String corpoInvalido = "";

        HttpHeaders cabecalhos = new HttpHeaders();
        cabecalhos.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requisicao = new HttpEntity<>(corpoInvalido, cabecalhos);

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", requisicao, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoEmailComCaixaDiferente_recebe401() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", "TESTE@teste.com");
        credenciais.put("senha", "Teste1235");

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postUsuarios_quandoLogado_recebeContentTypeJson() {
        Map<String, String> credenciais = new HashMap<>();
        credenciais.put("email", "teste@teste.com");
        credenciais.put("senha", "Teste1235");

        ResponseEntity<Object> resposta = templateTesteRest.postForEntity("/usuarios/login", credenciais, Object
                .class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);

        MediaType tipoConteudo = resposta.getHeaders().getContentType();
        assertThat(tipoConteudo).isNotNull();

        assertThat(tipoConteudo.isCompatibleWith(MediaType.APPLICATION_JSON)).isTrue();
    }
}

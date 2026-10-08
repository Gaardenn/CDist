package com.ursominhoco.cdist;

import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class AuthServiceTest {
    @Autowired
    private final TestRestTemplate templateTesteRest;
    @Autowired
    private final AuthService authService;

    @BeforeAll
    public void configura() {
        UsuarioDTO usuario = new UsuarioDTO("Teste Teste", "teste@teste.com", "Teste1235");
        templateTesteRest.postForEntity("/usuarios/cadastro", usuario, Object.class);
    }

    @Test
    public void carregaUsuarioPorEmail_quandoUsuarioExiste_recebeUsuario() {
        Usuario usuario = authService.carregaUsuarioPorEmail("teste@teste.com");
        assertThat(usuario).isNotNull();
    }

    @Test
    public void carregaUsuarioPorEmail_quandoUsuarioInexistente_recebeNull() {
        Usuario usuario = authService.carregaUsuarioPorEmail("teste@teste.com");
        assertThat(usuario).isNull();
    }
}

package com.ursominhoco.cdist;

import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import com.ursominhoco.cdist.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class AuthServiceTest {
    @Autowired
    private TestRestTemplate templateTesteRest;
    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void limpa() {
        usuarioRepository.deleteAll();
    }

    @Test
    public void carregaUsuarioPorEmail_quandoUsuarioExiste_recebeUsuario() {
        UsuarioDTO usuario = new UsuarioDTO("Teste Teste", "teste@teste.com", "Teste1235");
        templateTesteRest.postForEntity("/usuarios/cadastro", usuario, Object.class);
        UserDetails usuarioAchado = authService.loadUserByUsername("teste@teste.com");
        assertThat(usuarioAchado).isNotNull();
    }

    @Test
    public void carregaUsuarioPorEmail_quandoUsuarioInexistente_recebeExcecao() {
        UsuarioDTO usuario = new UsuarioDTO("Teste Teste", "teste@teste.com", "Teste1235");
        templateTesteRest.postForEntity("/usuarios/cadastro", usuario, Object.class);
        assertThatThrownBy(() -> authService.loadUserByUsername("naoteste@naoteste.com"))
                .isInstanceOf(UsernameNotFoundException.class).hasMessage("Usuário não encontrado!");
    }
}

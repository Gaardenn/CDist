package com.ursominhoco.cdist;

import com.ursominhoco.cdist.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class UsuarioTest {
    @Test
    public void getAuthorities_quandoCriado_eUsuario() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        boolean eUsuario = usuario.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
        assertThat(eUsuario).isTrue();
    }

    @Test
    public void getNome_quandoCriado_existe() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        String nome = usuario.getNome();
        assertThat(nome).isNotNull();
    }

    @Test
    public void getEmail_quandoCriado_existe() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        String email = usuario.getEmail();
        assertThat(email).isNotNull();
    }

    @Test
    public void getSenha_quandoCriado_existe() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        String senha = usuario.getSenha();
        assertThat(senha).isNotNull();
    }

    @Test
    public void isContaNaoExpirada_quandoCriado_recebeVerdadeiro() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        boolean contaNaoExpirada = usuario.isAccountNonExpired();
        assertThat(contaNaoExpirada).isTrue();
    }

    @Test
    public void isContaNaoTrancada_quandoCriado_recebeVerdadeiro() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        boolean contaNaoTrancada = usuario.isAccountNonLocked();
        assertThat(contaNaoTrancada).isTrue();
    }

    @Test
    public void isCredenciaisNaoExpiradas_quandoCriado_recebeVerdadeiro() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        boolean credenciaisNaoExpiradas = usuario.isCredentialsNonExpired();
        assertThat(credenciaisNaoExpiradas).isTrue();
    }

    @Test
    public void isHabilitada_quandoCriado_recebeVerdadeiro() {
        Usuario usuario = new Usuario(1L, "Teste Teste", "teste@teste.com", "Teste1235");
        boolean habilitada = usuario.isEnabled();
        assertThat(habilitada).isTrue();
    }
}

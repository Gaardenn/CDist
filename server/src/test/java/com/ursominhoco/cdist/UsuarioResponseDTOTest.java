package com.ursominhoco.cdist;

import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.security.dto.AuthorityResponseDTO;
import com.ursominhoco.cdist.security.dto.UsuarioResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class UsuarioResponseDTOTest {
    @Test
    public void userResponseDTO_quandoCriado_recebeCopiaEmailNome() {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("Teste1235");
        UsuarioResponseDTO usuarioRespostaDTO = new UsuarioResponseDTO(usuario);
        assertThat(usuarioRespostaDTO.getNome()).isEqualTo(usuario.getNome());
        assertThat(usuarioRespostaDTO.getEmail()).isEqualTo(usuario.getEmail());
    }

    @Test
    public void userResponseDTO_quandoCriado_recebeCopiaAuthoritiesAuthorityResponseDTO() {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste Teste");
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("Teste1235");
        UsuarioResponseDTO usuarioRespostaDTO = new UsuarioResponseDTO(usuario);
        assertThat(usuarioRespostaDTO.getAuthorities()).isInstanceOf(AuthorityResponseDTO.class);
        assertThat(usuarioRespostaDTO.getAuthorities().stream().anyMatch(auth -> auth
                .getAutoridade().equals("ROLE_USER"))).isTrue();
    }
}
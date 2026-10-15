package com.ursominhoco.cdist;

import com.ursominhoco.cdist.dto.AuthRequestDTO;
import com.ursominhoco.cdist.dto.EnderecoDTO;
import com.ursominhoco.cdist.dto.ItemPedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoResponseDTO;
import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.enums.MetodoPagamento;
import com.ursominhoco.cdist.enums.StatusPedido;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.PedidoRepository;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import com.ursominhoco.cdist.security.dto.AuthenticationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class PedidoControllerTest {
    private static final String API_PEDIDOS = "/pedidos";
    private static final Long PRODUTO_MARIO = 3L;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    @AfterEach
    public void limparBanco() {
        pedidoRepository.deleteAll();
        enderecoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    public void postPedidos_semToken_recebeUNAUTHORIZED() {
        Long enderecoQualquer = 123L;
        PedidoRequestDTO pedido = criarPedidoValido(enderecoQualquer);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_PEDIDOS, pedido, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postPedidos_quandoPedidoEValido_recebeCREATED() {
        String token = cadastrarELogar("teste@teste.com");
        Long enderecoId = criarEndereco(token);

        PedidoRequestDTO pedido = criarPedidoValido(enderecoId);

        ResponseEntity<PedidoResponseDTO> response = testRestTemplate.postForEntity(
                API_PEDIDOS, comToken(pedido, token), PedidoResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getStatus()).isEqualTo(StatusPedido.PENDENTE);
        assertThat(response.getBody().getTotal()).isEqualByComparingTo(new BigDecimal("699.98"));
    }

    private String cadastrarELogar(String email) {
        UsuarioDTO usuario = new UsuarioDTO("Teste Teste", email, "Teste1235");
        testRestTemplate.postForEntity("/usuarios/cadastro", usuario, Object.class);

        AuthRequestDTO login = new AuthRequestDTO();
        login.setEmail(email);
        login.setSenha("Teste1235");
        ResponseEntity<AuthenticationResponse> response = testRestTemplate.postForEntity(
                "/usuarios/login", login, AuthenticationResponse.class);

        return response.getBody().getToken();
    }

    private Long criarEndereco(String token) {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setCep("88800000");
        endereco.setLogradouro("Logradouro");
        endereco.setNumero("123");
        endereco.setComplemento("Complemento");
        endereco.setBairro("Bairro");
        endereco.setCidade("Cidade");
        endereco.setEstado("PR");

        ResponseEntity<EnderecoDTO> response = testRestTemplate.postForEntity(
                "/enderecos", comToken(endereco, token), EnderecoDTO.class);

        return response.getBody().getId();
    }

    private PedidoRequestDTO criarPedidoValido(Long enderecoId) {
        PedidoRequestDTO pedido = new PedidoRequestDTO();
        pedido.setEnderecoId(enderecoId);
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setItens(List.of(new ItemPedidoRequestDTO(PRODUTO_MARIO, 2)));
        return pedido;
    }

    private <T> HttpEntity<T> comToken(T corpo, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(corpo, headers);
    }
}
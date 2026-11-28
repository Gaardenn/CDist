package com.ursominhoco.cdist;

import com.ursominhoco.cdist.client.LogisticaClient;
import com.ursominhoco.cdist.dto.AuthRequestDTO;
import com.ursominhoco.cdist.dto.EnderecoDTO;
import com.ursominhoco.cdist.dto.ItemPedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoResponseDTO;
import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.enums.MetodoPagamento;
import com.ursominhoco.cdist.enums.StatusPedido;
import com.ursominhoco.cdist.exception.LogisticaIndisponivelException;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.PedidoRepository;
import com.ursominhoco.cdist.repository.ProdutoRepository;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class PedidoControllerTest {
    private static final String API_PEDIDOS = "/pedidos";
    private static final Long PRODUTO_MARIO = 3L;  // digital
    private static final Long PRODUTO_RED_DEAD = 6L;  // físico, R$ 199,99
    private static final int ESTOQUE_INICIAL = 10;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    // A API de logística não existe nos testes: o cliente é substituído por um mock
    @MockitoBean
    private LogisticaClient logisticaClient;

    @BeforeEach
    @AfterEach
    public void limparBanco() {
        pedidoRepository.deleteAll();
        enderecoRepository.deleteAll();
        usuarioRepository.deleteAll();
        definirEstoque(PRODUTO_MARIO, ESTOQUE_INICIAL);
        definirEstoque(PRODUTO_RED_DEAD, ESTOQUE_INICIAL);
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
        assertThat(response.getBody().getFrete()).isEqualByComparingTo(BigDecimal.ZERO);
        verifyNoInteractions(logisticaClient);  // como só tem item digital não é pra chamar a API de logística
    }

    @Test
    public void postPedidos_comItemFisico_gravaFreteDaLogisticaEBaixaEstoque() {
        when(logisticaClient.cotarFrete(any(), eq(2))).thenReturn(new BigDecimal("15.00"));
        String token = cadastrarELogar("teste@teste.com");
        Long enderecoId = criarEndereco(token);

        ResponseEntity<PedidoResponseDTO> response = testRestTemplate.postForEntity(
                API_PEDIDOS, comToken(criarPedido(enderecoId, PRODUTO_RED_DEAD, 2), token), PedidoResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getFrete()).isEqualByComparingTo(new BigDecimal("15.00"));
        assertThat(response.getBody().getTotal()).isEqualByComparingTo(new BigDecimal("414.98"));  // 2 x 199,99 + 15
        assertThat(estoqueDe(PRODUTO_RED_DEAD)).isEqualTo(ESTOQUE_INICIAL - 2);
        verify(logisticaClient).cotarFrete("88800000", 2);
    }

    @Test
    public void postPedidos_comEstoqueInsuficiente_recebeCONFLICTESemBaixarEstoque() {
        String token = cadastrarELogar("teste@teste.com");
        Long enderecoId = criarEndereco(token);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(
                API_PEDIDOS, comToken(criarPedido(enderecoId, PRODUTO_RED_DEAD, ESTOQUE_INICIAL + 1), token), Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(estoqueDe(PRODUTO_RED_DEAD)).isEqualTo(ESTOQUE_INICIAL);
        verifyNoInteractions(logisticaClient);
    }

    @Test
    public void postPedidos_comLogisticaForaDoAr_recebeSERVICEUNAVAILABLEESemBaixarEstoque() {
        when(logisticaClient.cotarFrete(any(), eq(1)))
                .thenThrow(new LogisticaIndisponivelException("Serviço de logística indisponível"));
        String token = cadastrarELogar("teste@teste.com");
        Long enderecoId = criarEndereco(token);

        ResponseEntity<Object> response = testRestTemplate.postForEntity(
                API_PEDIDOS, comToken(criarPedido(enderecoId, PRODUTO_RED_DEAD, 1), token), Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        assertThat(estoqueDe(PRODUTO_RED_DEAD)).isEqualTo(ESTOQUE_INICIAL);
        assertThat(pedidoRepository.count()).isZero();
    }

    private void definirEstoque(Long produtoId, int estoque) {
        produtoRepository.findById(produtoId).ifPresent(produto -> {
            produto.setEstoque(estoque);
            produtoRepository.save(produto);
        });
    }

    private int estoqueDe(Long produtoId) {
        return produtoRepository.findById(produtoId).orElseThrow().getEstoque();
    }

    private String cadastrarELogar(String email) {
        UsuarioDTO usuario = new UsuarioDTO("Teste teste", email, "Teste1235");
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
        return criarPedido(enderecoId, PRODUTO_MARIO, 2);
    }

    private PedidoRequestDTO criarPedido(Long enderecoId, Long produtoId, int quantidade) {
        PedidoRequestDTO pedido = new PedidoRequestDTO();
        pedido.setEnderecoId(enderecoId);
        pedido.setMetodoPagamento(MetodoPagamento.PIX);
        pedido.setItens(List.of(new ItemPedidoRequestDTO(produtoId, quantidade)));
        return pedido;
    }

    private <T>HttpEntity<T> comToken(T corpo, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(corpo, headers);
    }
}
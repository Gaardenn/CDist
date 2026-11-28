package com.ursominhoco.cdist;

import com.ursominhoco.cdist.client.LogisticaClient;
import com.ursominhoco.cdist.dto.ItemPedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoRequestDTO;
import com.ursominhoco.cdist.entity.Endereco;
import com.ursominhoco.cdist.entity.Pedido;
import com.ursominhoco.cdist.entity.Produto;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.enums.Midia;
import com.ursominhoco.cdist.enums.MetodoPagamento;
import com.ursominhoco.cdist.enums.StatusPedido;
import com.ursominhoco.cdist.exception.EstoqueInsuficienteException;
import com.ursominhoco.cdist.exception.LogisticaIndisponivelException;
import com.ursominhoco.cdist.exception.RecursoNaoEncontradoException;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.PedidoRepository;
import com.ursominhoco.cdist.repository.ProdutoRepository;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import com.ursominhoco.cdist.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class PedidoServiceTest {

    private static final String EMAIL = "bernardo@teste.com";
    private static final Long ENDERECO_ID = 1L;
    private static final String CEP = "85580001";

    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final EnderecoRepository enderecoRepository = mock(EnderecoRepository.class);
    private final ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
    private final LogisticaClient logisticaClient = mock(LogisticaClient.class);

    private final PedidoService pedidoService = new PedidoService(
            pedidoRepository, usuarioRepository, enderecoRepository, produtoRepository, logisticaClient);

    @BeforeEach
    void preparar() {
        Usuario usuario = Usuario.builder().id(1L).nome("Bernardo Lucini").email(EMAIL).senha("Umi1235!").build();
        Endereco endereco = Endereco.builder().id(ENDERECO_ID).usuario(usuario).cep(CEP)
                .logradouro("Rua Presidente Vargas").numero("167").bairro("Centro").cidade("Itapejara D'Oeste").estado("PR").build();

        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(usuario);
        when(enderecoRepository.findByIdAndUsuarioEmail(ENDERECO_ID, EMAIL)).thenReturn(endereco);
        when(produtoRepository.baixarEstoque(anyLong(), anyInt())).thenReturn(1);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocacao -> invocacao.getArgument(0));
    }

    @Test
    void criar_comDescontoEFrete_calculaTotalCorreto() {
        cadastrar(produto(2L, "Halo 3", Midia.FISICA, "49.99", "0.20", 5));
        cadastrar(produto(3L, "Super Mario Odyssey", Midia.DIGITAL, "349.99", "0.00", 5));
        when(logisticaClient.cotarFrete(CEP, 2)).thenReturn(new BigDecimal("15.00"));

        Pedido pedido = pedidoService.criar(pedido(ENDERECO_ID, item(2L, 2), item(3L, 1)), EMAIL);

        // Halo 3: 49,99 com 20% de desconto fica 39,99 * 2 = 79,98. Mario: 349,99. Frete: 15,00
        assertThat(pedido.getFrete()).isEqualByComparingTo("15.00");
        assertThat(pedido.getTotal()).isEqualByComparingTo("444.97");
        assertThat(pedido.getStatus()).isEqualTo(StatusPedido.PENDENTE);
        assertThat(pedido.getItens()).hasSize(2);
        assertThat(pedido.getCep()).isEqualTo(CEP);
        verify(logisticaClient).cotarFrete(CEP, 2);  // só o Halo 3 é mídia física
        verify(produtoRepository).baixarEstoque(2L, 2);
        verify(produtoRepository).baixarEstoque(3L, 1);
    }

    @Test
    void criar_soItensDigitais_freteZeroSemChamarALogistica() {
        cadastrar(produto(3L, "Super Mario Odyssey", Midia.DIGITAL, "349.99", "0.00", 5));

        Pedido pedido = pedidoService.criar(pedido(ENDERECO_ID, item(3L, 1)), EMAIL);

        assertThat(pedido.getFrete()).isEqualByComparingTo("0");
        assertThat(pedido.getTotal()).isEqualByComparingTo("349.99");
        verifyNoInteractions(logisticaClient);
    }

    @Test
    void criar_estoqueInsuficiente_lancaConflitoSemChamarLogisticaNemBaixarEstoque() {
        cadastrar(produto(1L, "God of War Ragnarök", Midia.FISICA, "187.87", "0.00", 1));

        assertThatThrownBy(() -> pedidoService.criar(pedido(ENDERECO_ID, item(1L, 2)), EMAIL))
                .isInstanceOf(EstoqueInsuficienteException.class)
                .hasMessageContaining("God of War Ragnarök");

        verifyNoInteractions(logisticaClient);
        verify(produtoRepository, never()).baixarEstoque(anyLong(), anyInt());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void criar_ultimasUnidadesLevadasPorOutroPedido_lancaConflitoESemSalvar() {
        // a checagem inicial passa (estoque 5), mas a baixa atômica não encontra mais unidades
        cadastrar(produto(2L, "Halo 3", Midia.FISICA, "49.99", "0.20", 5));
        when(logisticaClient.cotarFrete(CEP, 2)).thenReturn(new BigDecimal("15.00"));
        when(produtoRepository.baixarEstoque(2L, 2)).thenReturn(0);

        assertThatThrownBy(() -> pedidoService.criar(pedido(ENDERECO_ID, item(2L, 2)), EMAIL))
                .isInstanceOf(EstoqueInsuficienteException.class);

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void criar_enderecoDeOutroUsuario_lancaNaoEncontradoSemTocarEmProdutoOuLogistica() {
        Long enderecoDeOutroUsuario = 99L;  // o repositório só devolve endereço que pertence ao e-mail do token

        assertThatThrownBy(() -> pedidoService.criar(pedido(enderecoDeOutroUsuario, item(3L, 1)), EMAIL))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessage("Endereço não encontrado");

        verifyNoInteractions(produtoRepository, logisticaClient);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void criar_logisticaForaDoAr_propagaErroSemBaixarEstoqueNemSalvar() {
        cadastrar(produto(2L, "Halo 3", Midia.FISICA, "49.99", "0.20", 5));
        when(logisticaClient.cotarFrete(anyString(), anyInt()))
                .thenThrow(new LogisticaIndisponivelException("Serviço de logística indisponível"));

        assertThatThrownBy(() -> pedidoService.criar(pedido(ENDERECO_ID, item(2L, 1)), EMAIL))
                .isInstanceOf(LogisticaIndisponivelException.class);

        verify(produtoRepository, never()).baixarEstoque(anyLong(), anyInt());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void criar_mesmoProdutoDuasVezes_somaAsQuantidadesEmUmItemSo() {
        cadastrar(produto(3L, "Super Mario Odyssey", Midia.DIGITAL, "349.99", "0.00", 5));

        Pedido pedido = pedidoService.criar(pedido(ENDERECO_ID, item(3L, 1), item(3L, 2)), EMAIL);

        assertThat(pedido.getItens()).hasSize(1);
        assertThat(pedido.getItens().get(0).getQuantidade()).isEqualTo(3);
        verify(produtoRepository).baixarEstoque(3L, 3);
    }

    // apoio

    private Produto produto(Long id, String nome, Midia midia, String preco, String desconto, int estoque) {
        return Produto.builder()
                .id(id).nome(nome).midia(midia)
                .preco(new BigDecimal(preco)).desconto(new BigDecimal(desconto))
                .estoque(estoque)
                .build();
    }

    private void cadastrar(Produto produto) {
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
    }

    private ItemPedidoRequestDTO item(Long produtoId, int quantidade) {
        return new ItemPedidoRequestDTO(produtoId, quantidade);
    }

    private PedidoRequestDTO pedido(Long enderecoId, ItemPedidoRequestDTO... itens) {
        return new PedidoRequestDTO(enderecoId, MetodoPagamento.PIX, List.of(itens));
    }
}
package com.ursominhoco.cdist.service;

import com.ursominhoco.cdist.client.LogisticaClient;
import com.ursominhoco.cdist.dto.ItemPedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoRequestDTO;
import com.ursominhoco.cdist.entity.*;
import com.ursominhoco.cdist.enums.Midia;
import com.ursominhoco.cdist.enums.StatusPedido;
import com.ursominhoco.cdist.exception.EstoqueInsuficienteException;
import com.ursominhoco.cdist.exception.RecursoNaoEncontradoException;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.PedidoRepository;
import com.ursominhoco.cdist.repository.ProdutoRepository;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ProdutoRepository produtoRepository;
    private final LogisticaClient logisticaClient;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository,
                         EnderecoRepository enderecoRepository, ProdutoRepository produtoRepository,
                         LogisticaClient logisticaClient) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.produtoRepository = produtoRepository;
        this.logisticaClient = logisticaClient;
    }

    @Transactional(readOnly = true)
    public List<Pedido> obterTodosDoUsuario(String email) {
        return pedidoRepository.findByUsuarioEmailOrderByDataDesc(email);
    }

    @Transactional(readOnly = true)
    public Pedido obterPorId(Long id, String email) {
        Pedido pedido = pedidoRepository.findByIdAndUsuarioEmail(id, email);
        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido não encontrado");
        }
        return pedido;
    }

    @Transactional
    public Pedido criar(PedidoRequestDTO pedidoRequestDTO, String email) {

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        Endereco endereco = enderecoRepository.findByIdAndUsuarioEmail(pedidoRequestDTO.getEnderecoId(), email);
        if (endereco == null) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado");
        }

        // Produto repetido vira um item só. O TreeMap ordena por id, então os produtos são sempre
        // atualizados na mesma ordem para eivtar deadlock entre pedidos que acontecem ao mesmo tempo
        Map<Long, Integer> quantidades = new TreeMap<>();
        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
            quantidades.merge(itemDTO.getProdutoId(), itemDTO.getQuantidade(), Integer::sum);
        }

        // Produtos existem e têm estoque. Falha antes de chamar a logística
        Map<Long, Produto> produtos = new LinkedHashMap<>();
        int itensFisicos = 0;
        for (Map.Entry<Long, Integer> entrada : quantidades.entrySet()) {
            Long produtoId = entrada.getKey();
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto " + produtoId + " não encontrado"));

            if (produto.getEstoque() < entrada.getValue()) {
                throw new EstoqueInsuficienteException(produto.getNome(), produto.getEstoque(), entrada.getValue());
            }
            if (produto.getMidia() == Midia.FISICA) {
                itensFisicos += entrada.getValue();
            }
            produtos.put(produtoId, produto);
        }

        // Frete oficial, pedido para a API de logística antes de diminuir o estoque (pra não travar esperando a rede)
        BigDecimal frete = calcularFrete(endereco.getCep(), itensFisicos);

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .data(LocalDateTime.now())
                .status(StatusPedido.PENDENTE)
                .metodoPagamento(pedidoRequestDTO.getMetodoPagamento())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .frete(frete)
                .build();

        // Baixa atômica do estoque e criação dos itens. Se algum falhar, a transação desfaz tudo
        for (Map.Entry<Long, Integer> entrada : quantidades.entrySet()) {
            Produto produto = produtos.get(entrada.getKey());
            int quantidade = entrada.getValue();

            if (produtoRepository.baixarEstoque(entrada.getKey(), quantidade) == 0) {
                // outro pedido levou as últimas unidades depois da checagem do passo 1
                throw new EstoqueInsuficienteException(produto.getNome(), produto.getEstoque(), quantidade);
            }

            pedido.adicionarItem(PedidoProduto.builder()
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoUnitario(produto.getPreco())
                    .desconto(produto.getDesconto())
                    .build());
        }

        BigDecimal somaItens = pedido.getItens().stream()
                .map(PedidoProduto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(somaItens.add(pedido.getFrete()));

        return pedidoRepository.save(pedido);
    }

    /** RN01: se tiver só item digital o frete é zero e não depende da logística. */
    private BigDecimal calcularFrete(String cep, int itensFisicos) {
        if (itensFisicos == 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return logisticaClient.cotarFrete(cep, itensFisicos);
    }
}
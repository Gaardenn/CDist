package com.ursominhoco.cdist.service;

import com.ursominhoco.cdist.dto.ItemPedidoRequestDTO;
import com.ursominhoco.cdist.dto.PedidoRequestDTO;
import com.ursominhoco.cdist.entity.*;
import com.ursominhoco.cdist.enums.StatusPedido;
import com.ursominhoco.cdist.exception.RecursoNaoEncontradoException;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.PedidoRepository;
import com.ursominhoco.cdist.repository.ProdutoRepository;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository,
                         EnderecoRepository enderecoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.produtoRepository = produtoRepository;
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
                .build();

        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Produto " + itemDTO.getProdutoId() + " não encontrado"));

            PedidoProduto item = PedidoProduto.builder()
                    .produto(produto)
                    .quantidade(itemDTO.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .desconto(produto.getDesconto())
                    .build();

            pedido.adicionarItem(item);
        }

        BigDecimal somaItens = pedido.getItens().stream()
                .map(PedidoProduto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(somaItens.add(pedido.getFrete()));

        return pedidoRepository.save(pedido);
    }
}

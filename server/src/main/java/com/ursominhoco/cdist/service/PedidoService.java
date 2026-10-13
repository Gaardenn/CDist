package com.ursominhoco.cdist.service;

import com.ursominhoco.cdist.entity.Pedido;
import com.ursominhoco.cdist.repository.PedidoRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<Pedido> obterTodosDoUsuario(String email) {
        return pedidoRepository.findByUsuarioEmailOrderByDataDesc(email);
    }

    @Transactional(readOnly = true)
    public Pedido obterPorId(Long id, String email) {
        return pedidoRepository.findByIdAndUsuarioEmail(id, email);
    }
}

package com.ursominhoco.cdist.repository;

import com.ursominhoco.cdist.entity.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @EntityGraph(attributePaths = {"itens", "itens.produto"})
    List<Pedido> findByUsuarioEmailOrderByDataDesc(String email);

    @EntityGraph(attributePaths = {"itens", "itens.produto"})
    Pedido findByIdAndUsuarioEmail(Long id, String email);
}
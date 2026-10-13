package com.ursominhoco.cdist.repository;

import com.ursominhoco.cdist.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioEmailOrderByDataDesc(String email);

    Pedido findByIdAndUsuarioEmail(Long id, String email);
}

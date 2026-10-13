package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.dto.PedidoResponseDTO;
import com.ursominhoco.cdist.entity.Pedido;
import com.ursominhoco.cdist.mapper.PedidoMapper;
import com.ursominhoco.cdist.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PedidoResponseDTO>> obterTodos(@AuthenticationPrincipal UserDetails usuario) {
        List<Pedido> pedidos = pedidoService.obterTodosDoUsuario(usuario.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(pedidos.stream().map(pedidoMapper::paraDto).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PedidoResponseDTO> obterPorId(@PathVariable Long id, @AuthenticationPrincipal UserDetails usuario) {
        Pedido pedido = pedidoService.obterPorId(id, usuario.getUsername());
        if (pedido == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidoMapper.paraDto(pedido));
    }
}

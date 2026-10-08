package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.mapper.UsuarioMapper;
import com.ursominhoco.cdist.service.UsuarioService;
import com.ursominhoco.cdist.shared.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        usuarioService.salvar(usuarioMapper.paraEntidade(usuarioDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Usuário criado com sucesso!"));
    }
}
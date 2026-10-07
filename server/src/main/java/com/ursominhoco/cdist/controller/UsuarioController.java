package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.service.UsuarioService;
import com.ursominhoco.cdist.shared.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response> criarUsuario(@RequestBody Usuario usuario) {
        usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Usuário criado com sucesso!"));
    }
}
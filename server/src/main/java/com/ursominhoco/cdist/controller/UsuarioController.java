package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.service.UsuarioService;
import org.springframework.http.HttpStatus;
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
    void criarUsuario(@RequestBody Usuario usuario) {
        usuarioService.salvar(usuario);
    }
}
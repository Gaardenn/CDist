package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.dto.EnderecoDTO;
import com.ursominhoco.cdist.entity.Endereco;
import com.ursominhoco.cdist.mapper.EnderecoMapper;
import com.ursominhoco.cdist.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;

    public EnderecoController(EnderecoService enderecoService, EnderecoMapper enderecoMapper) {
        this.enderecoService = enderecoService;
        this.enderecoMapper = enderecoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestBody @Valid EnderecoDTO endereco, @AuthenticationPrincipal UserDetails usuario) {
        Endereco enderecoSalvo = enderecoService.salvar(enderecoMapper.paraEntidade(endereco), usuario.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoMapper.paraDto(enderecoSalvo));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EnderecoDTO>> obterTodos(@AuthenticationPrincipal UserDetails usuario) {
        List<Endereco> enderecos = enderecoService.obterTodosDoUsuario(usuario.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(enderecos.stream().map(enderecoMapper::paraDto).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EnderecoDTO> obterPorId(@PathVariable Long id, @AuthenticationPrincipal UserDetails usuario) {
        Endereco endereco = enderecoService.obterPorId(id, usuario.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(enderecoMapper.paraDto(endereco));
    }
}

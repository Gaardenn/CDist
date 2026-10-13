package com.ursominhoco.cdist.service;

import com.ursominhoco.cdist.entity.Endereco;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.exception.RecursoNaoEncontradoException;
import com.ursominhoco.cdist.repository.EnderecoRepository;
import com.ursominhoco.cdist.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco salvar(Endereco endereco, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        endereco.setId(null);
        endereco.setUsuario(usuario);
        return enderecoRepository.save(endereco);
    }

    @Transactional(readOnly = true)
    public List<Endereco> obterTodosDoUsuario(String email) {
        return enderecoRepository.findByUsuarioEmail(email);
    }

    @Transactional(readOnly = true)
    public Endereco obterPorId(Long id, String email) {
        return enderecoRepository.findByIdAndUsuarioEmail(id, email);
    }
}

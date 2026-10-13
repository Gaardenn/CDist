package com.ursominhoco.cdist.service;

import com.ursominhoco.cdist.entity.Produto;
import com.ursominhoco.cdist.exception.RecursoNaoEncontradoException;
import com.ursominhoco.cdist.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> listar(Long categoriaId, Pageable pageable) {
        if (categoriaId == null) {
            return produtoRepository.findAll(pageable);
        }
        return produtoRepository.findByCategoriaId(categoriaId, pageable);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));
    }
}

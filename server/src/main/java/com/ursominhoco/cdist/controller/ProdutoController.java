package com.ursominhoco.cdist.controller;

import com.ursominhoco.cdist.dto.ProdutoDetalheDTO;
import com.ursominhoco.cdist.dto.ProdutoResumoDTO;
import com.ursominhoco.cdist.mapper.ProdutoMapper;
import com.ursominhoco.cdist.service.ProdutoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtos")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProdutoResumoDTO>> listar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));

        Page<ProdutoResumoDTO> pagina = produtoService.listar(categoriaId, pageRequest).map(produtoMapper::paraResumoDto);

        return ResponseEntity.ok(new PagedModel<>(pagina));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoDetalheDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoMapper.paraDetalheDto(produtoService.buscarPorId(id)));
    }
}

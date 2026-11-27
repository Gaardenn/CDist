package com.ursominhoco.cdist.repository;

import com.ursominhoco.cdist.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findByCategoriaId(Long categoriaId, Pageable pageable);

    /**
     * Diminui o estoque de forma atômica: só diminui se tiver a quantidade pedida.
     * Devolve a quantidade de linhas alteradas (0 significa estoque insuficiente).
     */
    @Modifying
    @Query("update Produto p set p.estoque = p.estoque - :quantidade where p.id = :id and p.estoque >= :quantidade")
    int baixarEstoque(@Param("id") Long id, @Param("quantidade") int quantidade);
}
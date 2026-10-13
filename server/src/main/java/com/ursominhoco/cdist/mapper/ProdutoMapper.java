package com.ursominhoco.cdist.mapper;

import com.ursominhoco.cdist.dto.CategoriaResumoDTO;
import com.ursominhoco.cdist.dto.ProdutoDetalheDTO;
import com.ursominhoco.cdist.dto.ProdutoResumoDTO;
import com.ursominhoco.cdist.entity.Categoria;
import com.ursominhoco.cdist.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProdutoMapper {

    @Mapping(target = "precoFinal", expression = "java(calcularPrecoFinal(produto))")
    @Mapping(target = "status", expression = "java(calcularStatus(produto))")
    ProdutoResumoDTO paraResumoDto(Produto produto);

    @Mapping(target = "precoFinal", expression = "java(calcularPrecoFinal(produto))")
    @Mapping(target = "avaliacao", expression = "java(calcularMediaAvaliacoes(produto))")
    @Mapping(target = "distribuicaoAvaliacoes", expression = "java(montarDistribuicao(produto))")
    ProdutoDetalheDTO paraDetalheDto(Produto produto);

    CategoriaResumoDTO paraCategoriaResumoDto(Categoria categoria);

    default BigDecimal calcularPrecoFinal(Produto produto){
        BigDecimal fator = BigDecimal.ONE.subtract(produto.getDesconto());
        return produto.getPreco().multiply(fator).setScale(2, RoundingMode.HALF_DOWN);
    }

    default String calcularStatus(Produto produto){
        return produto.getEstoque() > 0 ? "EM_ESTOQUE" : "ESGOTADO";
    }

    default Double calcularMediaAvaliacoes(Produto produto){
        int total = produto.getQuantidadeAvaliacoes();
        if (total == 0) { return 0.0; }
        int soma = produto.getAvaliacaoNotaUm()
            + 2 * produto.getAvaliacaoNotaDois()
            + 3 * produto.getAvaliacaoNotaTres()
            + 4 * produto.getAvaliacaoNotaQuatro()
            + 5 * produto.getAvaliacaoNotaCinco();
        return Math.round((double) soma / total * 10) / 10.0;
    }

    default Map<String, Integer> montarDistribuicao(Produto produto){
        Map<String, Integer> distribuicao = new LinkedHashMap<>();
        distribuicao.put("5", produto.getAvaliacaoNotaCinco());
        distribuicao.put("4", produto.getAvaliacaoNotaQuatro());
        distribuicao.put("3", produto.getAvaliacaoNotaTres());
        distribuicao.put("2", produto.getAvaliacaoNotaDois());
        distribuicao.put("1", produto.getAvaliacaoNotaUm());
        return distribuicao;
    }
}

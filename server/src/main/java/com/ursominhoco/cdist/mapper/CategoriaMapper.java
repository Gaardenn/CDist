package com.ursominhoco.cdist.mapper;

import com.ursominhoco.cdist.dto.CategoriaDTO;
import com.ursominhoco.cdist.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    CategoriaDTO paraDTO(Categoria categoria);

    List<CategoriaDTO> paraDTO(List<Categoria> categorias);
}

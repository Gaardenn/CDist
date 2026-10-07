package com.ursominhoco.cdist.mapper;

import com.ursominhoco.cdist.dto.UsuarioDTO;
import com.ursominhoco.cdist.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    Usuario paraEntidade(UsuarioDTO usuarioDTO);

    UsuarioDTO paraDto(Usuario usuario);
}
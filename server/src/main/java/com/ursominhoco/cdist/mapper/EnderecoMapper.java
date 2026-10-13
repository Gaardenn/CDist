package com.ursominhoco.cdist.mapper;

import com.ursominhoco.cdist.dto.EnderecoDTO;
import com.ursominhoco.cdist.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnderecoMapper {
    EnderecoDTO paraDto(Endereco entidade);

    Endereco paraEntidade(EnderecoDTO enderecoDTO);
}

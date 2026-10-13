package com.ursominhoco.cdist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaResumoDTO {
    private Long id;
    private String nome;
    private String slug;
}

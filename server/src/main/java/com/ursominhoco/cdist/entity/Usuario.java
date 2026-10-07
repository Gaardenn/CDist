package com.ursominhoco.cdist.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private String nome;
    private String email;
    private String senha;
}

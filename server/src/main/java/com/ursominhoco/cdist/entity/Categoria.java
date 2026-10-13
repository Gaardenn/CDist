package com.ursominhoco.cdist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String nome;

    @NotNull
    @Column(unique = true)
    private String slug;

    @NotNull
    private String descricao;

    @NotNull
    private String imagem;
}

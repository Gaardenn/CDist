package com.ursominhoco.cdist.dto;

import com.ursominhoco.cdist.entity.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EnderecoDTO {
    private Long id;

    @NotNull
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    private String cidade;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}", message = "Estado deve ter 2 letras maiúsculas")
    private String estado;
}

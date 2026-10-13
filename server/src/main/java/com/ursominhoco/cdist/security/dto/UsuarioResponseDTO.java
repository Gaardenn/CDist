package com.ursominhoco.cdist.security.dto;

import com.ursominhoco.cdist.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private String nome;
    private String email;
    private Set<AuthorityResponseDTO> authorities;

    public UsuarioResponseDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.authorities = new HashSet<>();
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            authorities.add(new AuthorityResponseDTO(authority.getAuthority()));
        }
    }
}
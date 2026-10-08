package com.ursominhoco.cdist.repository;

import com.ursominhoco.cdist.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario encontrarUsuarioPorEmail(String email);
}
package com.ursominhoco.cdist.repository;

import com.ursominhoco.cdist.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUsuarioEmail(String email);

    Endereco findByIdAndUsuarioEmail(Long id, String email);
}

package com.Squad45.demoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Squad45.demoApp.entities.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}

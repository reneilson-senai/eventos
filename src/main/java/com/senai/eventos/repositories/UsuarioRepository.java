package com.senai.eventos.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.domain.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
}

package com.senai.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.senai.eventos.models.Token;
import com.senai.eventos.models.Usuario;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
    @Modifying
    int deleteByUsuario(Usuario usuario);
}

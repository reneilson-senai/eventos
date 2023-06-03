package com.senai.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.models.TokenSenha;

public interface TokenSenhaRepository extends JpaRepository<TokenSenha, Long>{
    Optional<TokenSenha> findByToken(String token); 
}

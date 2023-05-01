package com.senai.eventos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.models.Participacao;
import com.senai.eventos.models.ParticipacaoKey;

public interface ParticipacaoRepository extends JpaRepository<Participacao, ParticipacaoKey>{
    
}

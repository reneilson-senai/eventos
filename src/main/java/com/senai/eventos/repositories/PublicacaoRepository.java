package com.senai.eventos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.domain.publicacao.Publicacao;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    Page<?> findByEvento_Id(Pageable page, Long id);
    
}

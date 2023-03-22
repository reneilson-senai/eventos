package com.senai.eventos.domain.publicacao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    Page<Publicacao> findByEvento_Id(Pageable page, Long id);

    Page<Publicacao> findByOriginal_Id(Pageable pageable, Long id);
    
}

package com.senai.eventos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    Page<Evento> findByOrganizador_Id(Pageable page, Long id);
    Page<Evento> findByParticipacao_Pessoa_Id(Pageable page, Long id);
}

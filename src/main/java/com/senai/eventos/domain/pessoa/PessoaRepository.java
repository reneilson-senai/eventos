package com.senai.eventos.domain.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    Page<Pessoa> findByParticipacao_Evento_Id(Pageable page, Long eventoId);
}

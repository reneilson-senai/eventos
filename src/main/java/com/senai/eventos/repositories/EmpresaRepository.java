package com.senai.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.domain.empresa.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
}

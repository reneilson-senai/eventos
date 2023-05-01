package com.senai.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.eventos.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
}

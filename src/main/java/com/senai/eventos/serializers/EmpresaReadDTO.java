package com.senai.eventos.serializers;

import com.senai.eventos.models.Empresa;

public record EmpresaReadDTO(
    Long id,
    String nome, 
    String email, 
    String nomeFantasia,
    String cnpj
    ) {

        public EmpresaReadDTO(Empresa empresa){
            this(empresa.getId(), empresa.getNome(), empresa.getEmail(), empresa.getNomeFantasia(), empresa.getCnpj());
        }
}

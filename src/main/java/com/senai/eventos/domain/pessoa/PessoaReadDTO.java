package com.senai.eventos.domain.pessoa;

import java.time.LocalDate;

import com.senai.eventos.domain.endereco.EnderecoDTO;

public record PessoaReadDTO(
    Long id,
    String nome, 
    String email, 
    String sobrenome,
    EnderecoDTO endereco,
    LocalDate dataNascimento
    ) {

        public PessoaReadDTO(Pessoa pessoa){
            this(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getSobrenome(), new EnderecoDTO(pessoa.getEndereco()), pessoa.getDataNascimento());
        }
}

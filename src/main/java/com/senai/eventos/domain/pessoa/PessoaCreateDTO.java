package com.senai.eventos.domain.pessoa;

import java.time.LocalDate;

import com.senai.eventos.domain.endereco.EnderecoDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PessoaCreateDTO(
    @NotEmpty
    String nome, 

    @NotEmpty
    @Email
    String email,

    @NotEmpty 
    @Size(min = 8, message = "password should have at least 8 characters")
    String senha,

    String sobrenome,

    @NotEmpty
    LocalDate dataNascimento,

    EnderecoDTO endereco
    ) {
}

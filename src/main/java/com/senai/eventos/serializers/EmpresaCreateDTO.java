package com.senai.eventos.serializers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmpresaCreateDTO(
    @NotEmpty
    String nome, 

    @NotEmpty
    @Email
    String email,

    @NotEmpty 
    @Size(min = 8, message = "password should have at least 8 characters")
    String senha,

    String nomeFantasia,

    @NotEmpty
    String cnpj
    ) {
}

package com.senai.eventos.domain.endereco;

import jakarta.validation.constraints.NotEmpty;

public record EnderecoDTO(
    @NotEmpty
    String rua,

    @NotEmpty
    String numero,

    @NotEmpty
    String bairro,

    @NotEmpty
    String cidade,

    @NotEmpty
    String estado,

    @NotEmpty
    String cep,

    String pais
    ) {

        public EnderecoDTO(Endereco endereco){
            this(endereco.getRua(), endereco.getNumero(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado(), endereco.getCep(), endereco.getPais());
        }
}

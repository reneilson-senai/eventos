package com.senai.eventos.serializers;

import com.senai.eventos.models.Pessoa;
import java.time.LocalDate;

public record PessoaReadDTO(
  Long id,
  String nome,
  String email,
  String sobrenome,
  EnderecoDTO endereco,
  LocalDate dataNascimento
) {
  public PessoaReadDTO(Pessoa pessoa) {
    this(
      pessoa.getId(),
      pessoa.getNome(),
      pessoa.getEmail(),
      pessoa.getSobrenome(),
      new EnderecoDTO(pessoa.getEndereco()),
      pessoa.getDataNascimento()
    );
  }
}

package com.senai.eventos.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.senai.eventos.serializers.PessoaCreateDTO;
import com.senai.eventos.serializers.PessoaReadDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoas")
public class Pessoa extends Usuario {

  private String sobrenome;
  private LocalDate dataNascimento;

  @Embedded
  private Endereco endereco;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Pessoa> amigos = new ArrayList<Pessoa>();

  @ManyToMany(mappedBy = "seguidores",cascade = CascadeType.ALL)
  private List<Empresa> seguindo = new ArrayList<Empresa>();

  @OneToMany(mappedBy = "pessoa",cascade = CascadeType.ALL)
  private List<Participacao> participacao = new ArrayList<Participacao>();

  public Pessoa(PessoaCreateDTO dto) {
    super(dto.nome(), dto.email(), dto.senha(), dto.papel());
    this.sobrenome = dto.sobrenome();
    this.dataNascimento = dto.dataNascimento();
    this.endereco = new Endereco(dto.endereco());
  }

  public void update(PessoaReadDTO dto) {
    if (dto.email() != null) {
      this.setEmail(dto.email());
    }
    if (dto.nome() != null) {
      this.setNome(dto.nome());
    }
    if (dto.dataNascimento() != null) {
      this.setDataNascimento(dto.dataNascimento());
    }
    if (dto.sobrenome() != null) {
      this.setSobrenome(dto.sobrenome());
    }
    if (dto.endereco() != null) {
      this.getEndereco().update(dto.endereco());
    }
  }
}

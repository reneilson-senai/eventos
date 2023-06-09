package com.senai.eventos.serializers;

import com.senai.eventos.models.Publicacao;

import jakarta.validation.constraints.NotEmpty;

public record PublicacaoDTO(
  Long id,

  @NotEmpty Long publicador_id,

  @NotEmpty String conteudo,

  @NotEmpty Long evento_id
) {
  public PublicacaoDTO(Publicacao publicacao) {
    this(
      publicacao.getId(),
      publicacao.getPublicador().getId(),
      publicacao.getConteudo(),
      publicacao.getEvento() != null ? publicacao.getEvento().getId() : null
    );
  }
}

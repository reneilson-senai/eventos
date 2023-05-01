package com.senai.eventos.serializers;

import com.senai.eventos.models.Publicacao;

import jakarta.validation.constraints.NotEmpty;

public record ComentarioDTO(
  Long id,

  @NotEmpty Long publicador_id,

  @NotEmpty Long original_id,

  @NotEmpty String conteudo

) {
  public ComentarioDTO(Publicacao publicacao) {
    this(
      publicacao.getId(),
      publicacao.getPublicador().getId(),
      publicacao.getOriginal().getId(),
      publicacao.getConteudo()
    );
  }
}

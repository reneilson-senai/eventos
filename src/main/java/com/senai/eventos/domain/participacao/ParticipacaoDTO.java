package com.senai.eventos.domain.participacao;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public record ParticipacaoDTO(
    @NotEmpty
    Long eventoId,
    @NotEmpty
    Long pessoaId,
    LocalDateTime dataParticipacao
) {
    public ParticipacaoDTO(Participacao participacao){
        this(participacao.getEvento().getId(), participacao.getPessoa().getId(), participacao.getDataParticipacao());
    }
}

package com.senai.eventos.serializers;

import java.time.LocalDateTime;

import com.senai.eventos.models.Participacao;

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

package com.senai.eventos.serializers;

import java.time.LocalDateTime;

import com.senai.eventos.models.Evento;

import jakarta.validation.constraints.NotEmpty;

public record EventoDTO(
    Long id,

    @NotEmpty
    String nome,

    @NotEmpty
    LocalDateTime dataInicio,

    @NotEmpty
    LocalDateTime dataFim,

    @NotEmpty
    EnderecoDTO local,

    @NotEmpty
    Long organizador_id,

    String descricao,

    Boolean visibilidade
    ) {
        public EventoDTO(Evento evento){
            this(
                evento.getId(),
                evento.getNome(),
                evento.getDataInicio(),
                evento.getDataFim(),
                new EnderecoDTO(evento.getLocal()),
                evento.getOrganizador().getId(),
                evento.getDescricao(),
                evento.getVisibilidade()    
            );
        }
}

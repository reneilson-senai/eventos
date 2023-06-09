package com.senai.eventos.models;

import java.time.LocalDateTime;

import com.senai.eventos.serializers.ParticipacaoDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="participacoes")
public class Participacao {
    @EmbeddedId
    private ParticipacaoKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("eventoId")
    @JoinColumn(name="evento_id")
    private Evento evento;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("pessoaId")
    @JoinColumn(name="pessoa_id")
    private Pessoa pessoa;

    private LocalDateTime dataParticipacao = LocalDateTime.now(); 

    public Participacao(ParticipacaoDTO dto){
        this.id = new ParticipacaoKey(dto.eventoId(), dto.pessoaId());
        this.evento = new Evento();
        this.evento.setId(dto.eventoId());
        this.pessoa = new Pessoa();
        this.pessoa.setId(dto.pessoaId());
    }

    public Participacao(Evento ev, Pessoa ps){
        this.id = new ParticipacaoKey(ev.getId(), ps.getId());
        this.evento = ev;
        this.pessoa = ps;
    }
}

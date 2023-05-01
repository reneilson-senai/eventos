package com.senai.eventos.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParticipacaoKey implements Serializable {
    @Column(name="evento_id")
    Long eventoId;
    @Column(name="pessoa_id")
    Long pessoaId;
}

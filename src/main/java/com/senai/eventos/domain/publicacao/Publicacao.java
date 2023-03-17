package com.senai.eventos.domain.publicacao;
import com.senai.eventos.domain.evento.Evento;
import com.senai.eventos.domain.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="publicacoes")
public class Publicacao {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1028)
    private String conteudo;
    private Integer relevancia = 0;
    private String midia;
    @ManyToOne
    private Usuario publicador;
    @ManyToOne
    private Evento evento;

    public Publicacao(PublicacaoDTO dto){
        this.conteudo = dto.conteudo();
        this.midia = dto.midia();
        this.evento = new Evento();
        this.evento.setId(dto.evento_id());
    }

    public void update(PublicacaoDTO dto){
        if(dto.conteudo() != null){
            this.setConteudo(dto.conteudo());
        }
        if(dto.midia() != null){
            this.setMidia(dto.midia());
        }
    }

    public void increaseRelevancia(Publicacao publicacao){
        publicacao.setRelevancia(publicacao.getRelevancia()+1);
    }

    public void decreaseRelevancia(Publicacao publicacao){
        publicacao.setRelevancia(publicacao.getRelevancia()-1);
    }
}

package com.senai.eventos.domain.publicacao;
import java.time.LocalDateTime;
import java.util.List;

import com.senai.eventos.domain.FotoModel;
import com.senai.eventos.domain.evento.Evento;
import com.senai.eventos.domain.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="publicacoes")
public class Publicacao extends FotoModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1028)
    private String conteudo;
    private Integer relevancia = 0;
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne(optional = false)
    private Usuario publicador;
    @ManyToOne(optional = true)
    private Evento evento;
    @OneToOne(optional = true)
    private Publicacao original;
    @ManyToMany
    private List<Publicacao> comentarios;

    public Publicacao(PublicacaoDTO dto){
        this.conteudo = dto.conteudo();
        this.evento = new Evento();
        this.evento.setId(dto.evento_id());
        this.publicador = new Usuario();
        this.publicador.setId(dto.publicador_id());
    }

    public Publicacao(ComentarioDTO dto){
        this.conteudo = dto.conteudo();
        this.publicador = new Usuario();
        this.publicador.setId(dto.publicador_id());
        this.original = new Publicacao();
        this.original.setId(dto.original_id());
    }

    public void update(PublicacaoDTO dto){
        if(dto.conteudo() != null){
            this.setConteudo(dto.conteudo());
        }
    }

    public void increaseRelevancia(){
        this.setRelevancia(this.getRelevancia()+1);
    }

    public void decreaseRelevancia(){
        this.setRelevancia(this.getRelevancia()-1);
    }

    public void addComentario(Publicacao comentario){
        this.comentarios.add(comentario);
        comentario.setOriginal(this);
    }
}

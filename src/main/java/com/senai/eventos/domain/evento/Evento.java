package com.senai.eventos.domain.evento;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.senai.eventos.domain.FotoModel;
import com.senai.eventos.domain.endereco.Endereco;
import com.senai.eventos.domain.file.FileInfo;
import com.senai.eventos.domain.participacao.Participacao;
import com.senai.eventos.domain.publicacao.Publicacao;
import com.senai.eventos.domain.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="eventos")
public class Evento extends FotoModel {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @Embedded
    private Endereco local;
    @ManyToOne
    private Usuario organizador;
    @Column(length = 1028)
    private String descricao;
    private Boolean visibilidade; 
    
    @OneToMany(mappedBy = "evento")
    private List<Participacao> participacao = new ArrayList<Participacao>();

    @OneToMany(mappedBy = "evento")
    private List<Publicacao> publicacoes = new ArrayList<Publicacao>();

    public Evento(EventoDTO dto){
        this.nome = dto.nome();
        this.dataInicio = dto.dataInicio();
        this.dataFim = dto.dataFim();
        this.local = new Endereco(dto.local());
        this.descricao = dto.descricao();
        this.visibilidade = true;
        this.organizador = new Usuario();
        this.organizador.setId(dto.organizador_id());
    }

    public void update(EventoDTO dto){
        if(dto.nome() != null){
            this.setNome(dto.nome());
        }
        if(dto.dataInicio() != null){
            this.setDataInicio(dto.dataInicio());
        }
        if(dto.dataFim() != null){
            this.setDataFim(dto.dataFim());
        }
        if(dto.local() != null){
            this.getLocal().update(dto.local());
        }
        if(dto.descricao() != null){
            this.setNome(dto.descricao());
        }
        if(dto.visibilidade() != null){
            this.setVisibilidade(dto.visibilidade());
        }
    }
}

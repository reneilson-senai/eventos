package com.senai.eventos.domain.usuario;

import java.util.ArrayList;
import java.util.List;

import com.senai.eventos.domain.evento.Evento;
import com.senai.eventos.domain.file.FileInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    @Embedded
    private FileInfo foto;

    @OneToMany(mappedBy = "organizador")
    private List<Evento> eventosOrganizando = new ArrayList<Evento>();

    public Usuario(UsuarioDTO dto){
        this.nome = dto.nome();
        this.email = dto.email();
    }

    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void addEvento(Evento evento){
        evento.setOrganizador(this);
        this.eventosOrganizando.add(evento);
    }
}

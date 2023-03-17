package com.senai.eventos.domain.pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.senai.eventos.domain.empresa.Empresa;
import com.senai.eventos.domain.endereco.Endereco;
import com.senai.eventos.domain.evento.Evento;
import com.senai.eventos.domain.usuario.Usuario;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pessoas")
public class Pessoa extends Usuario{
    private String sobrenome;
    private LocalDate dataNascimento;
    @Embedded
    private Endereco endereco;

    @ManyToMany
    private List<Pessoa> amigos = new ArrayList<Pessoa>();

    @ManyToMany(mappedBy = "seguidores")
    private List<Empresa> seguindo = new ArrayList<Empresa>();

    @ManyToMany(mappedBy = "participantes")
    private List<Evento> eventos = new ArrayList<Evento>();

    public Pessoa(PessoaCreateDTO dto){
        super(dto.nome(), dto.email(), dto.senha());
        this.sobrenome = dto.sobrenome();
        this.dataNascimento = dto.dataNascimento();
        this.endereco = new Endereco(dto.endereco());
    }

    public void update(PessoaReadDTO dto){
        if(dto.email() != null){
            this.setEmail(dto.email());
        }
        if(dto.nome() != null){
            this.setNome(dto.nome());
        }
        if(dto.dataNascimento() != null){
            this.setDataNascimento(dto.dataNascimento());
        }
        if(dto.sobrenome() != null){
            this.setSobrenome(dto.sobrenome());
        }
        if(dto.endereco() != null){
            this.getEndereco().update(dto.endereco());
        }
    }
}

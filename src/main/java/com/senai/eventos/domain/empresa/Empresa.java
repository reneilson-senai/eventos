package com.senai.eventos.domain.empresa;

import java.util.ArrayList;
import java.util.List;

import com.senai.eventos.domain.pessoa.Pessoa;
import com.senai.eventos.domain.usuario.Usuario;

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
@Table(name="empresas")
public class Empresa extends Usuario {
    private String nomeFantasia;
    private String cnpj;
    @ManyToMany
    private List<Pessoa> seguidores = new ArrayList<Pessoa>();

    public Empresa(EmpresaCreateDTO dto){
        super(dto.nome(), dto.email(), dto.senha());
        this.nomeFantasia = dto.nomeFantasia();
        this.cnpj = dto.cnpj();
    }

    public void update(EmpresaReadDTO dto){
        if(dto.email() != null){
            this.setEmail(dto.email());
        }
        if(dto.nome() != null){
            this.setNome(dto.nome());
        }
        if(dto.nomeFantasia() != null){
            this.setNomeFantasia(dto.nomeFantasia());
        }
        if(dto.cnpj() != null){
            this.setCnpj(dto.cnpj());
        }
    }
}

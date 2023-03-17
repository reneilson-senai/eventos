package com.senai.eventos.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais = "Brasil";
    private String cep; 

    public Endereco(EnderecoDTO dto){
        this.rua = dto.rua();
        this.numero = dto.numero();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.cep = dto.cep();
    }

    public void update(EnderecoDTO dto){
        if(dto.rua() != null){
            this.setRua(dto.rua());
        }
        if(dto.numero() != null){
            this.setNumero(dto.numero());
        }
        if(dto.rua() != null){
            this.setRua(dto.rua());
        }
        if(dto.bairro() != null){
            this.setBairro(dto.bairro());
        }
        if(dto.cidade() != null){
            this.setCidade(dto.cidade());
        }
        if(dto.estado() != null){
            this.setEstado(dto.estado());
        }
        if(dto.cep() != null){
            this.setCep(dto.cep());
        }
    }
}

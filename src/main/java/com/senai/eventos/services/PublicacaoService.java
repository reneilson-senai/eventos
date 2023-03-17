package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.domain.publicacao.Publicacao;
import com.senai.eventos.domain.publicacao.PublicacaoDTO;
import com.senai.eventos.domain.publicacao.PublicacaoRepository;

@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository repository;

    private Publicacao getById(Long id){
        if(repository.existsById(id)){
            var publicacao = repository.findById(id).get();
            return publicacao;
        }
        return null;
    }

    public PublicacaoDTO read(Long id){
        if(repository.existsById(id)){
            var publicacao = repository.findById(id).get();
            return new PublicacaoDTO(publicacao);
        }
        return null;
    }

    public PublicacaoDTO save(PublicacaoDTO data){
        Publicacao publicacao = this.repository.save(new Publicacao(data));
        return new PublicacaoDTO(publicacao);
    }

    public Page<PublicacaoDTO> listAll(Pageable pageable){
        var publicacaos = this.repository.findAll(pageable).map(PublicacaoDTO::new);
        return publicacaos;
    }

    public PublicacaoDTO update(PublicacaoDTO dto, Long id){
        var publicacao = this.getById(id);
        if(publicacao != null){
            publicacao.update(dto);
            repository.save(publicacao);
            return new PublicacaoDTO(publicacao);
        }
        return null;
    }

    public void delete(Long id){
        var publicacao = this.getById(id);
        if(publicacao != null){
            repository.delete(publicacao);
        }
    }
}

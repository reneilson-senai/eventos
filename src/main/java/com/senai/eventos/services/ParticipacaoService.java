package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.models.Participacao;
import com.senai.eventos.models.ParticipacaoKey;
import com.senai.eventos.repositories.ParticipacaoRepository;
import com.senai.eventos.serializers.ParticipacaoDTO;

@Service
public class ParticipacaoService {
    @Autowired
    private ParticipacaoRepository repository;

    private Participacao getById(Long eventoId, Long pessoaId){
        var key = new ParticipacaoKey(eventoId, pessoaId);
        if(repository.existsById(key)){
            var participacao = repository.findById(key).get();
            return participacao;
        }
        return null;
    }

    public ParticipacaoDTO read(Long eventoId, Long pessoaId){
        var key = new ParticipacaoKey(eventoId, pessoaId);
        if(repository.existsById(key)){
            var participacao = repository.findById(key).get();
            return new ParticipacaoDTO(participacao);
        }
        return null;
    }

    public ParticipacaoDTO save(ParticipacaoDTO data){
        Participacao participacao = this.repository.save(new Participacao(data));
        return new ParticipacaoDTO(participacao);
    }

    public Page<ParticipacaoDTO> listAll(Pageable pageable, Example<Participacao> ex){
        var participacaos = this.repository.findAll(ex, pageable).map(ParticipacaoDTO::new);
        return participacaos;
    }

    public void delete(Long eventoId, Long pessoaId){
        var participacao = this.getById(eventoId, pessoaId);
        if(participacao != null){
            repository.delete(participacao);
        }
    }
}

package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.domain.evento.Evento;
import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.domain.pessoa.PessoaReadDTO;
import com.senai.eventos.repositories.EventoRepository;
import com.senai.eventos.repositories.PessoaRepository;
import com.senai.eventos.repositories.PublicacaoRepository;
import com.senai.eventos.repositories.UsuarioRepository;

@Service
public class EventoService {
    @Autowired
    private EventoRepository repository;

    @Autowired 
    private PublicacaoRepository pRepository;

    @Autowired 
    private PessoaRepository psRepository;

    private Evento getById(Long id){
        if(repository.existsById(id)){
            var evento = repository.findById(id).get();
            return evento;
        }
        return null;
    }

    public EventoDTO read(Long id){
        if(repository.existsById(id)){
            var evento = repository.findById(id).get();
            return new EventoDTO(evento);
        }
        return null;
    }

    public EventoDTO save(EventoDTO data){
        Evento evento = this.repository.save(new Evento(data));
        return new EventoDTO(evento);
    }

    public Page<EventoDTO> listAll(Pageable pageable){
        var eventos = this.repository.findAll(pageable).map(EventoDTO::new);
        return eventos;
    }

    public EventoDTO update(EventoDTO dto, Long id){
        var evento = this.getById(id);
        if(evento != null){
            evento.update(dto);
            repository.save(evento);
            return new EventoDTO(evento);
        }
        return null;
    }

    public void delete(Long id){
        var evento = this.getById(id);
        if(evento != null){
            repository.delete(evento);
        }
    }

    public Page<?> listPublicacoes(Long id, Pageable page) {
        return pRepository.findByEvento_Id(page, id);
    }

    public Page<?> listParticipantes(Long id, Pageable page) {
        return psRepository.findByEventos_Id(page, id).map(PessoaReadDTO::new);
    }
}

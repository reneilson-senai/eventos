package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.domain.usuario.UsuarioDTO;
import com.senai.eventos.repositories.EventoRepository;
import com.senai.eventos.repositories.PessoaRepository;
import com.senai.eventos.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EventoRepository eventoRepository;

    public Page<UsuarioDTO> listAll(Pageable pageable){
        var usuarios = this.repository.findAll(pageable).map(UsuarioDTO::new);
        return usuarios;
    }

    public Page<EventoDTO> listEventos(Long id, Pageable page) {
        if(this.repository.existsById(id)){
            var eventos = eventoRepository.findByOrganizador_Id(page, id);
            return eventos.map(EventoDTO::new);
        }
        return null;
    }

    public Page<EventoDTO> listEventosParticipando(Long id, Pageable page) {
        if(this.repository.existsById(id)){
            var eventos = eventoRepository.findByParticipantes_Id(page, id);
            return eventos.map(EventoDTO::new);
        }
        return null;
    }
}

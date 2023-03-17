package com.senai.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.domain.usuario.UsuarioDTO;
import com.senai.eventos.services.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public Page<UsuarioDTO> list(Pageable page){
        return service.listAll(page);
    }

    @GetMapping("/{id}/organizador")
    public Page<EventoDTO> listEventosOrganizando(@PathVariable Long id, Pageable page){
        return service.listEventos(id, page);
    }

    @GetMapping("/{id}/participante")
    public Page<EventoDTO> listEventosParticipando(@PathVariable Long id, Pageable page){
        return service.listEventosParticipando(id, page);
    }
}

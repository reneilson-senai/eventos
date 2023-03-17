package com.senai.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.services.EventoService;

@RestController
@RequestMapping("eventos")
public class EventoController {
    @Autowired
    private EventoService service;

    @GetMapping
    public Page<EventoDTO> list(Pageable page){
        var itens = service.listAll(page);
        return itens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> read(@PathVariable Long id){
        return ResponseEntity.ok(service.read(id));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> create(@RequestBody EventoDTO evento){
        var created = service.save(evento);
        return new ResponseEntity<EventoDTO>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @RequestBody EventoDTO data){
        var evento = service.update(data, id);
        return new ResponseEntity<EventoDTO>(evento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/publicacoes")
    public Page<?> listPublicacoes(@PathVariable Long id, Pageable page){
        return service.listPublicacoes(id, page);
    }

    @GetMapping("/{id}/participantes")
    public Page<?> listParticipantes(@PathVariable Long id, Pageable page){
        return service.listParticipantes(id, page);
    }
}

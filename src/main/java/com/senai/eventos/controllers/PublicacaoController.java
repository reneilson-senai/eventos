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

import com.senai.eventos.domain.publicacao.PublicacaoDTO;
import com.senai.eventos.services.PublicacaoService;

@RestController
@RequestMapping("publicacaos")
public class PublicacaoController {
    @Autowired
    private PublicacaoService service;

    @GetMapping
    public Page<PublicacaoDTO> list(Pageable page){
        var itens = service.listAll(page);
        return itens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacaoDTO> read(@PathVariable Long id){
        return ResponseEntity.ok(service.read(id));
    }

    @PostMapping
    public ResponseEntity<PublicacaoDTO> create(@RequestBody PublicacaoDTO publicacao){
        var created = service.save(publicacao);
        return new ResponseEntity<PublicacaoDTO>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacaoDTO> update(@PathVariable Long id, @RequestBody PublicacaoDTO data){
        var publicacao = service.update(data, id);
        return new ResponseEntity<PublicacaoDTO>(publicacao, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

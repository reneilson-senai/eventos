package com.senai.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.eventos.serializers.PessoaCreateDTO;
import com.senai.eventos.serializers.PessoaReadDTO;
import com.senai.eventos.services.PessoaService;
import com.senai.eventos.services.UsuarioService;


@RestController
@Validated
@RequestMapping("pessoas")
public class PessoaController {
    @Autowired
    private PessoaService service;


    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Page<PessoaReadDTO> list(Pageable page){
        var itens = service.listAllPessoas(page);
        return itens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaReadDTO> read(@PathVariable Long id){
        return ResponseEntity.ok(service.read(id));
    }

    @PostMapping
    public ResponseEntity<PessoaReadDTO> create(@RequestBody PessoaCreateDTO pessoa){
        var created = service.save(pessoa);
        return new ResponseEntity<PessoaReadDTO>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaReadDTO> update(@PathVariable Long id, @RequestBody PessoaReadDTO data){
        var pessoa = service.update(data, id);
        return new ResponseEntity<PessoaReadDTO>(pessoa, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/eventos")
    public Page<?> listEventos(@PathVariable Long id, Pageable page){
        return usuarioService.listEventos(id, page);
    }
}

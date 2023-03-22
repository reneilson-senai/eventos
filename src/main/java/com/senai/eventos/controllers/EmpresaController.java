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

import com.senai.eventos.domain.empresa.EmpresaCreateDTO;
import com.senai.eventos.domain.empresa.EmpresaReadDTO;
import com.senai.eventos.services.EmpresaService;
import com.senai.eventos.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Page<EmpresaReadDTO> list(Pageable page){
        var itens = service.listAllEmpresas(page);
        return itens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaReadDTO> read(@PathVariable long id){
        return ResponseEntity.ok(service.read(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaReadDTO> create(@RequestBody @Valid EmpresaCreateDTO empresa){
        var created = service.save(empresa);
        return new ResponseEntity<EmpresaReadDTO>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaReadDTO> update(@PathVariable Long id, @RequestBody EmpresaReadDTO data){
        var empresa = service.update(data, id);
        return new ResponseEntity<EmpresaReadDTO>(empresa, HttpStatus.CREATED);
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

package com.senai.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.services.EmailService;
import com.senai.eventos.services.EventoService;

import jakarta.transaction.Transactional;

@RestController
@Validated
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
    @Transactional
    public ResponseEntity<EventoDTO> create(@RequestBody EventoDTO evento){
        var created = service.save(evento);
        return new ResponseEntity<EventoDTO>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @RequestBody EventoDTO data){
        var evento = service.update(data, id);
        return new ResponseEntity<EventoDTO>(evento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
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

    @PostMapping("/{id}/photo")
    @Transactional
    public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam MultipartFile file) {
      String message = "";
      try {
          service.upload(id, file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(message);
      } catch (Exception e) {
        message =
          "Could not upload the file: " +
          file.getOriginalFilename() +
          ". Error: " +
          e.getMessage();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
      }
    }
  
    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
      Resource file = service.getFoto(id);
      
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}

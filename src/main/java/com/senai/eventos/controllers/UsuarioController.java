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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.serializers.UsuarioDTO;
import com.senai.eventos.services.UsuarioService;

import jakarta.transaction.Transactional;

@RestController
@Validated
@RequestMapping("usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @GetMapping
  public Page<UsuarioDTO> list(Pageable page) {
    return service.listAll(page);
  }

  @GetMapping("/{id}/organizador")
  public Page<EventoDTO> listEventosOrganizando(
    @PathVariable Long id,
    Pageable page
  ) {
    return service.listEventos(id, page);
  }

  @GetMapping("/{id}/participante")
  public Page<EventoDTO> listEventosParticipando(
    @PathVariable Long id,
    Pageable page
  ) {
    return service.listEventosParticipando(id, page);
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

  @DeleteMapping("/{id}/photo")
  public ResponseEntity<?> deleteFoto(@PathVariable Long id){
    service.deleteFoto(id);
    return ResponseEntity.noContent().build();
  }
}

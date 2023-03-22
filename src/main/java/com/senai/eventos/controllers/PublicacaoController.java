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

import com.senai.eventos.domain.publicacao.ComentarioDTO;
import com.senai.eventos.domain.publicacao.PublicacaoDTO;
import com.senai.eventos.services.PublicacaoService;

import jakarta.transaction.Transactional;

@RestController
@Validated
@RequestMapping("publicacoes")
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

    @PutMapping("/{id}/curtir")
    public ResponseEntity<Integer> curtir(@PathVariable Long id){
        Integer curtidas = service.mudarRelevancia(id, true);
        return ResponseEntity.ok(curtidas);
    }

    @PutMapping("/{id}/descurtir")
    public ResponseEntity<Integer> descurtir(@PathVariable Long id){
        Integer curtidas = service.mudarRelevancia(id, false);
        return ResponseEntity.ok(curtidas);
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioDTO> comentar(@PathVariable Long id, @RequestBody ComentarioDTO dto){
        var comentario = service.criarComentario(id, dto);
        return new ResponseEntity<ComentarioDTO>(comentario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comentarios")
    public Page<ComentarioDTO> listAllComentarios(@PathVariable Long id, Pageable pageable){
        var comentarios = service.listComentarios(id, pageable);
        return comentarios;
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

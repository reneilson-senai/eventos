package com.senai.eventos.controllers;

import com.senai.eventos.models.Evento;
import com.senai.eventos.models.Participacao;
import com.senai.eventos.models.Pessoa;
import com.senai.eventos.serializers.ParticipacaoDTO;
import com.senai.eventos.services.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("participacoes")
public class ParticipacaoController {

  @Autowired
  private ParticipacaoService service;

  @GetMapping
  public Page<ParticipacaoDTO> list(
    Pageable page,
    @RequestParam(required = false) Long eventoId,
    @RequestParam(required = false) Long pessoaId
  ) {
    var participacao = new Participacao();
    if(eventoId != null){
        var ev = new Evento();
        ev.setId(eventoId);
        participacao.setEvento(ev);
    }
    if(pessoaId != null){
        var ps = new Pessoa();
        ps.setId(pessoaId);
        participacao.setPessoa(ps);
    }
    participacao.setDataParticipacao(null);
    Example<Participacao> ex = Example.of(participacao);
    var itens = service.listAll(page, ex);
    return itens;
  }

  @GetMapping("/evento/{eventoId}/pessoa/{pessoaId}")
  public ResponseEntity<ParticipacaoDTO> read(
    @PathVariable Long eventoId,
    @PathVariable Long pessoaId
  ) {
    return ResponseEntity.ok(service.read(eventoId, pessoaId));
  }

  @PostMapping
  public ResponseEntity<ParticipacaoDTO> create(
    @RequestBody ParticipacaoDTO participacao
  ) {
    var created = service.save(participacao);
    return new ResponseEntity<ParticipacaoDTO>(created, HttpStatus.CREATED);
  }

  @DeleteMapping("/evento/{eventoId}/pessoa/{pessoaId}")
  public ResponseEntity<?> delete(
    @PathVariable Long eventoId,
    @PathVariable Long pessoaId
  ) {
    service.delete(eventoId, pessoaId);
    return ResponseEntity.noContent().build();
  }
}

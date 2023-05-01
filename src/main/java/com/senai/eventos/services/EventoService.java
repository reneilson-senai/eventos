package com.senai.eventos.services;

import com.senai.eventos.models.Evento;
import com.senai.eventos.models.FileInfo;
import com.senai.eventos.repositories.EventoRepository;
import com.senai.eventos.repositories.PessoaRepository;
import com.senai.eventos.repositories.PublicacaoRepository;
import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.serializers.PessoaReadDTO;
import com.senai.eventos.serializers.PublicacaoDTO;
import com.senai.eventos.services.validations.Validation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EventoService {

  @Autowired
  private EventoRepository repository;

  @Autowired
  private PublicacaoRepository pRepository;

  @Autowired
  private PessoaRepository psRepository;

  @Autowired
  FilesStorageService storageService;

  @Autowired
  List<Validation<EventoDTO>> validadores;

  private Evento getById(Long id) {
    if (repository.existsById(id)) {
      var evento = repository.findById(id).get();
      return evento;
    }
    return null;
  }

  public EventoDTO read(Long id) {
    if (repository.existsById(id)) {
      var evento = repository.findById(id).get();
      return new EventoDTO(evento);
    }
    return null;
  }

  public EventoDTO save(EventoDTO data) {
    validadores.forEach(v -> v.validar(data));
    Evento evento = this.repository.save(new Evento(data));
    return new EventoDTO(evento);
  }

  public Page<EventoDTO> listAll(Pageable pageable) {
    var eventos = this.repository.findAll(pageable).map(EventoDTO::new);
    return eventos;
  }

  public EventoDTO update(EventoDTO dto, Long id) {
    validadores.forEach(v -> v.validar(dto));
    var evento = this.getById(id);
    if (evento != null) {
      evento.update(dto);
      repository.save(evento);
      return new EventoDTO(evento);
    }
    return null;
  }

  public void delete(Long id) {
    var evento = this.getById(id);
    if (evento != null) {
      repository.delete(evento);
    }
  }

  public Page<?> listPublicacoes(Long id, Pageable page) {
    return pRepository.findByEvento_Id(page, id).map(PublicacaoDTO::new);
  }

  public Page<?> listParticipantes(Long id, Pageable page) {
    return psRepository
      .findByParticipacao_Evento_Id(page, id)
      .map(PessoaReadDTO::new);
  }

  public void upload(Long id, MultipartFile file) {
    if (repository.existsById(id)) {
      var filename = storageService.save(file);
      var foto = new FileInfo(filename);
      var evento = repository.findById(id).get();
      evento.setFoto(foto);
      repository.save(evento);
    }
  }

  public Resource getFoto(Long id) {
    if (repository.existsById(id)) {
      var evento = repository.findById(id).get();
      if (evento.getFoto() != null) {
        return storageService.load(evento.getFoto().getFilename());
      }
      return null;
    }
    return null;
  }
}

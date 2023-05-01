package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senai.eventos.models.FileInfo;
import com.senai.eventos.repositories.EventoRepository;
import com.senai.eventos.repositories.UsuarioRepository;
import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.serializers.UsuarioDTO;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    FilesStorageService storageService;

    public Page<UsuarioDTO> listAll(Pageable pageable){
        var usuarios = this.repository.findAll(pageable).map(UsuarioDTO::new);
        return usuarios;
    }

    public Page<EventoDTO> listEventos(Long id, Pageable page) {
        if(this.repository.existsById(id)){
            var eventos = eventoRepository.findByOrganizador_Id(page, id);
            return eventos.map(EventoDTO::new);
        }
        return null;
    }

    public Page<EventoDTO> listEventosParticipando(Long id, Pageable page) {
        if(this.repository.existsById(id)){
            var eventos = eventoRepository.findByParticipacao_Pessoa_Id(page, id);
            return eventos.map(EventoDTO::new);
        }
        return null;
    }

    public void upload(Long id, MultipartFile file) {
        if(repository.existsById(id)){
            var filename = storageService.save(file);
            var foto = new FileInfo(filename);
            var usuario = repository.findById(id).get();
            usuario.setFoto(foto);
            repository.save(usuario);
        }
    }

    public Resource getFoto(Long id) {
        if(repository.existsById(id)){
            var usuario = repository.findById(id).get();
            if(usuario.getFoto().getFilename() != null){
                return storageService.load(usuario.getFoto().getFilename());
            }       
            return null;     
        }
        return null;
    }

    public void deleteFoto(Long id) {
        if(repository.existsById(id)){
            var usuario = repository.findById(id).get();
            if(usuario.getFoto() != null){
                storageService.delete(usuario.getFoto().getFilename());
                usuario.setFoto(null);
                repository.save(usuario);
            }           
        }
    }
}

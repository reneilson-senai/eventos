package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senai.eventos.domain.file.FileInfo;
import com.senai.eventos.domain.publicacao.ComentarioDTO;
import com.senai.eventos.domain.publicacao.Publicacao;
import com.senai.eventos.domain.publicacao.PublicacaoDTO;
import com.senai.eventos.domain.publicacao.PublicacaoRepository;

@Service
public class PublicacaoService {
    @Autowired
    private PublicacaoRepository repository;

    @Autowired
    FilesStorageService storageService;

    private Publicacao getById(Long id){
        if(repository.existsById(id)){
            var publicacao = repository.findById(id).get();
            return publicacao;
        }
        return null;
    }

    public PublicacaoDTO read(Long id){
        if(repository.existsById(id)){
            var publicacao = repository.findById(id).get();
            return new PublicacaoDTO(publicacao);
        }
        return null;
    }

    public PublicacaoDTO save(PublicacaoDTO data){
        Publicacao publicacao = this.repository.save(new Publicacao(data));
        return new PublicacaoDTO(publicacao);
    }

    public Page<PublicacaoDTO> listAll(Pageable pageable){
        var publicacaos = this.repository.findAll(pageable).map(PublicacaoDTO::new);
        return publicacaos;
    }

    public PublicacaoDTO update(PublicacaoDTO dto, Long id){
        var publicacao = this.getById(id);
        if(publicacao != null){
            publicacao.update(dto);
            repository.save(publicacao);
            return new PublicacaoDTO(publicacao);
        }
        return null;
    }

    public void delete(Long id){
        var publicacao = this.getById(id);
        if(publicacao != null){
            repository.delete(publicacao);
        }
    }

    public Integer mudarRelevancia(Long id, boolean increase) {
        var publicacao = this.getById(id);
        if(publicacao != null){
            if(increase){
                publicacao.increaseRelevancia();
            }else{
                publicacao.decreaseRelevancia();
            }
            repository.save(publicacao);
            return publicacao.getRelevancia();
        }
        return null;
    }

    public ComentarioDTO criarComentario(Long id, ComentarioDTO data){
        var publicacao = this.getById(id);
        if(publicacao != null){
            Publicacao comentario = this.repository.save(new Publicacao(data));
            publicacao.addComentario(comentario);
            repository.save(publicacao);
            return new ComentarioDTO(comentario);
        }
        return null;
    }

    public Page<ComentarioDTO> listComentarios(Long id, Pageable pageable){
        var comentarios = this.repository.findByOriginal_Id(pageable, id).map(ComentarioDTO::new);
        return comentarios;
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
            var publicacao = repository.findById(id).get();
            if(publicacao.getFoto().getFilename() != null){
                return storageService.load(publicacao.getFoto().getFilename());
            }       
            return null;     
        }
        return null;
    }
}

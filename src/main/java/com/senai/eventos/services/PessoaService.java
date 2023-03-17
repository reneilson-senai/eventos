package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.domain.pessoa.Pessoa;
import com.senai.eventos.domain.pessoa.PessoaCreateDTO;
import com.senai.eventos.domain.pessoa.PessoaReadDTO;
import com.senai.eventos.domain.pessoa.PessoaRepository;

@Service
public class PessoaService  {
    @Autowired
    private PessoaRepository repository;

    private Pessoa getById(Long id){
        if(repository.existsById(id)){
            var pessoa = repository.findById(id).get();
            return pessoa;
        }
        return null;
    }

    public PessoaReadDTO read(Long id){
        if(repository.existsById(id)){
            var pessoa = repository.findById(id).get();
            return new PessoaReadDTO(pessoa);
        }
        return null;
    }

    public PessoaReadDTO save(PessoaCreateDTO data){
        Pessoa pessoa = this.repository.save(new Pessoa(data));
        return new PessoaReadDTO(pessoa);
    }

    public Page<PessoaReadDTO> listAllPessoas(Pageable pageable){
        var pessoas = this.repository.findAll(pageable).map(PessoaReadDTO::new);
        return pessoas;
    }

    public PessoaReadDTO update(PessoaReadDTO dto, Long id){
        var pessoa = this.getById(id);
        if(pessoa != null){
            pessoa.update(dto);
            repository.save(pessoa);
            return new PessoaReadDTO(pessoa);
        }
        return null;
    }

    public void delete(Long id){
        var pessoa = this.getById(id);
        if(pessoa != null){
            repository.delete(pessoa);
        }
    }
}

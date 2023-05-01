package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.eventos.models.Pessoa;
import com.senai.eventos.repositories.PessoaRepository;
import com.senai.eventos.serializers.PessoaCreateDTO;
import com.senai.eventos.serializers.PessoaReadDTO;

@Service
public class PessoaService  {
    @Autowired
    private PessoaRepository repository;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        String encodedPassword = bCryptPasswordEncoder.encode(data.senha());
        Pessoa register = new Pessoa(data);
        register.setSenha(encodedPassword);
        Pessoa pessoa = this.repository.save(register);
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

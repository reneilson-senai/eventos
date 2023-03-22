package com.senai.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.senai.eventos.domain.empresa.Empresa;
import com.senai.eventos.domain.empresa.EmpresaCreateDTO;
import com.senai.eventos.domain.empresa.EmpresaReadDTO;
import com.senai.eventos.domain.empresa.EmpresaRepository;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository repository;

    private Empresa getById(Long id){
        if(repository.existsById(id)){
            var empresa = repository.findById(id).get();
            return empresa;
        }
        return null;
    }

    public EmpresaReadDTO read(Long id){
        var empresa = repository.findById(id).get();
        return new EmpresaReadDTO(empresa);
    }

    public EmpresaReadDTO save(EmpresaCreateDTO data){
        Empresa empresa = this.repository.save(new Empresa(data));
        return new EmpresaReadDTO(empresa);
    }

    public Page<EmpresaReadDTO> listAllEmpresas(Pageable pageable){
        var empresas = this.repository.findAll(pageable).map(EmpresaReadDTO::new);
        return empresas;
    }

    public EmpresaReadDTO update(EmpresaReadDTO dto, Long id){
        var empresa = this.getById(id);
        if(empresa != null){
            empresa.update(dto);
            repository.save(empresa);
            return new EmpresaReadDTO(empresa);
        }
        return null;
    }

    public void delete(Long id){
        var empresa = this.getById(id);
        if(empresa != null){
            repository.delete(empresa);
        }
    }
}

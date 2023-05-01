package com.senai.eventos.serializers;

import com.senai.eventos.models.Papel;
import com.senai.eventos.models.Usuario;

public record UsuarioDTO (
    Long id,

    String nome,

    String email,

    Papel papel
) {
    public UsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPapel());
    }
}

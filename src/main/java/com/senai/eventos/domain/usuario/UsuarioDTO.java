package com.senai.eventos.domain.usuario;

public record UsuarioDTO (
    Long id,

    String nome,

    String email
) {
    public UsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}

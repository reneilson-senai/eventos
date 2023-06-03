package com.senai.eventos.serializers;

import jakarta.validation.constraints.NotEmpty;

public record TokenSenhaDTO(
    @NotEmpty
    String token,
    @NotEmpty 
    String novaSenha,
    @NotEmpty
    String senhaAntiga
) { }

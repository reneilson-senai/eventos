package com.senai.eventos.serializers;

import com.senai.eventos.models.Token;

public record JWTResponseDTO(
    String token,
    String refreshToken,
    String email
) {
    public JWTResponseDTO(Token token){
        this(token.getToken(), token.getRefreshToken(), token.getUsuario().getEmail());
    }
}

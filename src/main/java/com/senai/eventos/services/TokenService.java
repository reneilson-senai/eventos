package com.senai.eventos.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.senai.eventos.domain.usuario.Usuario;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${api.security.token.issuer}")
  private String issuer;

  @Value("${api.security.token.expiration_min}")
  private Integer expirationMin;

  public String gerarToken(Usuario usuario) {
    try {
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT
        .create()
        .withIssuer(issuer)
        .withSubject(usuario.getEmail())
        .withExpiresAt(dataExpiracao())
        .sign(algoritmo);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao gerar token.", exception);
    }
  }

  private Instant dataExpiracao() {
    return LocalDateTime
      .now()
      .plusMinutes(expirationMin)
      .toInstant(ZoneOffset.of("-03:00"));
  }

  public String getSubject(String tokenJWT) {
    try {
      var algoritmo = Algorithm.HMAC256(secret);
      return JWT
        .require(algoritmo)
        .withIssuer(issuer)
        .build()
        .verify(tokenJWT)
        .getSubject();
    } catch (JWTVerificationException exception) {
      throw new RuntimeException("Token inválido ou expirado.");
    }
  }
}

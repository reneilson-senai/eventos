package com.senai.eventos.services;

import com.senai.eventos.models.Token;
import com.senai.eventos.models.Usuario;
import com.senai.eventos.repositories.TokenRepository;
import com.senai.eventos.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

  @Value("${api.security.refresh.expiration_sec}")
  private String refreshTokenDurationS;

  @Autowired
  private TokenRepository refreshTokenRepository;

  @Autowired
  private UsuarioRepository userRepository;

  public Optional<Token> findByToken(String refreshToken) {
    return refreshTokenRepository.findByRefreshToken(refreshToken);
  }

  public Token createRefreshToken(Long userId, String token) {
    Token refreshToken = new Token();
    refreshToken.setUsuario(userRepository.findById(userId).get());
    refreshToken.setExpirationTime(
      Instant.now().plusSeconds(Long.parseLong(refreshTokenDurationS))
    );
    refreshToken.setRefreshToken(UUID.randomUUID().toString());
    refreshToken.setToken(token);

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public Token verifyExpiration(Token token) {
    if (token.getExpirationTime().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new RuntimeException(
        "Refresh token was expired. Please make a new signin request"
      );
    }
    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUsuario(
      userRepository.findById(userId).get()
    );
  }
}

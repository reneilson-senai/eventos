package com.senai.eventos.controllers;

import com.senai.eventos.models.Token;
import com.senai.eventos.models.Usuario;
import com.senai.eventos.serializers.JWTRequestDTO;
import com.senai.eventos.serializers.JWTResponseDTO;
import com.senai.eventos.serializers.TokenRefreshDTO;
import com.senai.eventos.services.RefreshTokenService;
import com.senai.eventos.services.TokenService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@RestController
@Slf4j
public class AutenticacaoController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;
  
  @Autowired
  private RefreshTokenService refreshTokenService;

  @Transactional
  @PostMapping("/login")
  public ResponseEntity<JWTResponseDTO> login(
    @RequestBody @Valid JWTRequestDTO dados
  ) {
    log.info(dados.toString());
    var authenticationToken = new UsernamePasswordAuthenticationToken(
      dados.email(),
      dados.senha()
    );
    var authentication = manager.authenticate(authenticationToken);
    var usuario = (Usuario) authentication.getPrincipal();
    var token = tokenService.gerarToken(usuario);
    Token refreshToken = refreshTokenService.createRefreshToken(usuario.getId(), token);
    log.info(refreshToken.toString());
    return new ResponseEntity<JWTResponseDTO>(
      new JWTResponseDTO(refreshToken),
      HttpStatus.CREATED
    );
  }

  @PostMapping("/refresh")
  public ResponseEntity<JWTResponseDTO> refreshtoken(@Valid @RequestBody TokenRefreshDTO request) {
    String requestRefreshToken = request.refreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(Token::getUsuario)
        .map(user -> getToken(requestRefreshToken, user))
        .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
  }

  private ResponseEntity<JWTResponseDTO> getToken(String requestRefreshToken, Usuario usuario) {
    String token = tokenService.gerarToken(usuario);
    Token refreshToken = refreshTokenService.createRefreshToken(usuario.getId(), token);
    return ResponseEntity.ok(new JWTResponseDTO(refreshToken));
  }

  @DeleteMapping("/revoke")
  public ResponseEntity<?> revokeToken(Authentication authentication) {
    var usuario = (Usuario) authentication.getPrincipal();
    refreshTokenService.deleteByUserId(usuario.getId());
    return ResponseEntity.noContent().build();
  }
}

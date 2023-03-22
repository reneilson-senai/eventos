package com.senai.eventos.controllers;

import com.senai.eventos.domain.seguranca.JWTRequestDTO;
import com.senai.eventos.domain.seguranca.JWTResponseDTO;
import com.senai.eventos.domain.usuario.Usuario;
import com.senai.eventos.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<JWTResponseDTO> login(
    @RequestBody @Valid JWTRequestDTO dados
  ) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(
      dados.email(),
      dados.senha()
    );
    var authentication = manager.authenticate(authenticationToken);
    var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
    var tokenResponse = new JWTResponseDTO(token);
    return new ResponseEntity<JWTResponseDTO>(
      tokenResponse,
      HttpStatus.CREATED
    );
  }
}

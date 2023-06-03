package com.senai.eventos.controllers;

import com.senai.eventos.serializers.TokenSenhaDTO;
import com.senai.eventos.services.TokenSenhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tokenSenha")
public class TokenSenhaController {

  @Autowired
  private TokenSenhaService service;

  @GetMapping
  public ResponseEntity<String> esqueciSenha() {
    var token = this.service.createTokenSenha();
    return ResponseEntity.ok(token);
  }

  @PostMapping
  public ResponseEntity<String> mudarSenha(
    @Valid @RequestBody TokenSenhaDTO dto
  ) {
    var senhaAlterada = this.service.mudarSenha(dto);
    if (senhaAlterada) {
      return new ResponseEntity(
        "Senha alterada com sucesso",
        HttpStatus.CREATED
      );
    }
    return ResponseEntity.badRequest().body("Erro ao tentar alterar a senha");
  }
}

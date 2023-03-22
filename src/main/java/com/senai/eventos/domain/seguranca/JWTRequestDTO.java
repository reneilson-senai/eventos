package com.senai.eventos.domain.seguranca;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record JWTRequestDTO(
  @NotEmpty @Email String email,

  @NotEmpty String senha
) {}

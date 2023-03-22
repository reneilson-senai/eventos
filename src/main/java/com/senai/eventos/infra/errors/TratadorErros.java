package com.senai.eventos.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {

  @ExceptionHandler(
    { EntityNotFoundException.class, NoSuchElementException.class }
  )
  public ResponseEntity tratarErro404() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(
    MethodArgumentNotValidException.class
  )
  public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors().stream().map(CampoErroDTO::new).toList();
    return ResponseEntity.badRequest().body(erros);
  }

  @ExceptionHandler(
    ValidacaoException.class
  )
  public ResponseEntity tratarErro400(ValidacaoException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}

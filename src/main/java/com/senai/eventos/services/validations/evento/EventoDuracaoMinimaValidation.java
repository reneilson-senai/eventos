package com.senai.eventos.services.validations.evento;

import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.infra.errors.ValidacaoException;
import com.senai.eventos.services.validations.Validation;

import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class EventoDuracaoMinimaValidation implements Validation<EventoDTO> {

  @Override
  public void validar(EventoDTO evento) {
    var duracao = Duration.between(evento.dataFim(), evento.dataInicio()).toMinutes();
    if (duracao < 30) {
      throw new ValidacaoException(
        "A duração mínima de um evento deve ser de 30 minutos!"
      );
    }
  }
}

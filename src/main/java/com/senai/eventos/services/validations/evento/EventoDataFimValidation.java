package com.senai.eventos.services.validations.evento;

import com.senai.eventos.domain.evento.EventoDTO;
import com.senai.eventos.infra.errors.ValidacaoException;
import com.senai.eventos.services.validations.Validation;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class EventoDataFimValidation implements Validation<EventoDTO> {

  @Override
  public void validar(EventoDTO evento) {
    if (evento.dataFim().isBefore(LocalDateTime.now())) {
      throw new ValidacaoException(
        "Data de fim do evento precisa ser maior que a data atual."
      );
    }
  }
}

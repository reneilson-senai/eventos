package com.senai.eventos.services.validations.evento;

import com.senai.eventos.infra.errors.ValidacaoException;
import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.services.validations.Validation;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class EventoDataInicioValitation implements Validation<EventoDTO> {

  @Override
  public void validar(EventoDTO evento) {
    if (evento.dataInicio().isBefore(LocalDateTime.now())) {
      throw new ValidacaoException(
        "Data de início do evento precisa ser maior que a data atual."
      );
    }
  }
}

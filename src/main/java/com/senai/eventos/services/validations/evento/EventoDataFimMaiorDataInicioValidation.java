package com.senai.eventos.services.validations.evento;

import com.senai.eventos.infra.errors.ValidacaoException;
import com.senai.eventos.serializers.EventoDTO;
import com.senai.eventos.services.validations.Validation;
import org.springframework.stereotype.Component;

@Component
public class EventoDataFimMaiorDataInicioValidation implements Validation<EventoDTO> {

  @Override
  public void validar(EventoDTO evento) {
    if(evento.dataFim().isBefore(evento.dataInicio())){
      throw new ValidacaoException(
        "A data de fim do evento deve ser maior que a data de início."
      );
    }

  }
}

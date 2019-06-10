package com.altran.mamartin.beans.exceptions;

import com.altran.mamartin.beans.dto.ResultException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseAppException extends RuntimeException {

  private ResultException resultado;

  private Exception exception;

  /**
   * Constructor con un parametro String que contiene el mensaje del resultado.
   *
   * @param mensaje Cadena con el mensaje que mostrara el resultado.
   */
  public BaseAppException(final String mensaje) {
    super(mensaje);
    this.resultado = new ResultException(mensaje);
  }

  /**
   * Constructor con un parametro de tipo Excepcion.
   *
   * @param e Excepcion que controla
   */
  public BaseAppException(final Exception e) {
    super(e);
    this.resultado = new ResultException(e.getMessage());
    this.exception = e;
  }

}


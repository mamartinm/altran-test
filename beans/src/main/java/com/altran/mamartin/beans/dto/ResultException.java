package com.altran.mamartin.beans.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultException implements Serializable {

  private Serializable key;
  private String mensaje;

  public ResultException(final String mensaje) {
    this();
    this.mensaje = mensaje;
  }
}

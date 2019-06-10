package com.altran.mamartin.beans.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppNotFoundException extends BaseAppException {

  public AppNotFoundException(String mensaje) {
    super(mensaje);
  }

}

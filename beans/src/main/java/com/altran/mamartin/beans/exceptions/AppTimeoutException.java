package com.altran.mamartin.beans.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppTimeoutException extends BaseAppException {

  public AppTimeoutException(String mensaje) {
    super(mensaje);
  }

}

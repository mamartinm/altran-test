package com.altran.mamartin.webcontroller.handlers.error;

import com.altran.mamartin.beans.utils.Constants;
import java.util.Date;
import java.util.Map;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
class GlobalErrorAttributes extends DefaultErrorAttributes {

  private static final String MESSAGE = "message";
  private static final String DATE = "date";
  private static final String HA_OCURRIDO_UN_ERROR = "Ha ocurrido un error inesperado, vuelva a intentarlo en unos minutos";

  public GlobalErrorAttributes() {
    super(Boolean.TRUE);
  }

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
    Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);
    map.put(DATE, Constants.SDF.format(new Date()));
    map.put(MESSAGE, HA_OCURRIDO_UN_ERROR);
    return map;
  }
}

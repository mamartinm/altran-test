package com.altran.mamartin.webcontroller.config;

import com.altran.mamartin.beans.exceptions.AppNotFoundException;
import com.altran.mamartin.beans.exceptions.AppTimeoutException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The {@code GenericExceptionHandler} class represents...
 */
@Slf4j
public class GenericExceptionHandler {

  private static final String SE_HA_SUPERADO_EL_TIEMPO_DE_CONEXION = "Se ha superado el tiempo de conexion";
  private static final String NO_EXISTEN_RESULTADOS = "No hay resultados";

  private static final String HA_OCURRIDO_UN_ERROR = "Ha ocurrido un error";

  @ExceptionHandler(AppNotFoundException.class)
  ResponseEntity<String> handleNotFoundException(Exception e) throws JsonProcessingException {
    return new ResponseEntity<>(exceptionToJson(NO_EXISTEN_RESULTADOS, e), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AppTimeoutException.class)
  ResponseEntity<String> handleTimeoutException(Exception e) throws JsonProcessingException {
    return new ResponseEntity<>(exceptionToJson(SE_HA_SUPERADO_EL_TIEMPO_DE_CONEXION, e), new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
  }

  @ExceptionHandler
  ResponseEntity<String> handle(Exception e) throws JsonProcessingException {
    return new ResponseEntity<>(exceptionToJson(HA_OCURRIDO_UN_ERROR, e), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String exceptionToJson(String cause, Exception exception) throws JsonProcessingException {
    log.error("", exception);
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode rootNode = mapper.createObjectNode();
    rootNode.put("cause", cause);
    rootNode.put("message", exception.getMessage());
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
  }
}


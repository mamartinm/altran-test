package com.altran.mamartin.webcontroller.config;

import com.altran.mamartin.webcontroller.exceptions.AppNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The {@code GenericExceptionHandler} class represents...
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String SE_HA_SUPERADO_EL_TIEMPO_DE_CONEXION = "Se ha superado el tiempo de conexion al servidor del ayto";
  private static final String NO_EXISTEN_RESULTADOS = "No hay resultados";
  private static final String HA_OCURRIDO_UN_ERROR = "Ha ocurrido un error";

  @ExceptionHandler(value = AppNotFoundException.class)
  ResponseEntity<String> handleAppNotFound(AppNotFoundException e) throws JsonProcessingException {
    log.warn("La peticion no ha devuelto resultados");
    return new ResponseEntity<>(exceptionToJson(NO_EXISTEN_RESULTADOS, e), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ResourceAccessException.class)
  ResponseEntity<String> handleAppTimeout(ResourceAccessException e) throws JsonProcessingException {
    log.warn("La peticion tarda demasiado");
    return new ResponseEntity<>(exceptionToJson(SE_HA_SUPERADO_EL_TIEMPO_DE_CONEXION, e), new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRunTime(Exception e, WebRequest request) throws JsonProcessingException {
    log.error("Ha ocurrido un error", e);
    return new ResponseEntity<>(exceptionToJson(HA_OCURRIDO_UN_ERROR, e), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String exceptionToJson(String cause, Exception exception) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode rootNode = mapper.createObjectNode();
    rootNode.put("cause", cause);
    rootNode.put("message", exception.getMessage());
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
  }
}


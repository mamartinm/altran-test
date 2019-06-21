package com.altran.mamartin.webcontroller.handlers.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandlerConfig extends AbstractErrorWebExceptionHandler {

  public GlobalExceptionHandlerConfig(GlobalErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext,
      ServerCodecConfigurer configurer) {
    super(errorAttributes, resourceProperties, applicationContext);
    this.setMessageWriters(configurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    String exceptionMessage = (String) getErrorAttributes(request, Boolean.TRUE).get("trace");
    log.error(exceptionMessage);
    return ServerResponse.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(BodyInserters.fromObject(getErrorAttributes(request, Boolean.FALSE)));
  }

}

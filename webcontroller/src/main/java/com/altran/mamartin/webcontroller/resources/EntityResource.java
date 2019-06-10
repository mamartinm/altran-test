package com.altran.mamartin.webcontroller.resources;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.exceptions.AppNotFoundException;
import com.altran.mamartin.model.EntityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "entity")
public class EntityResource {

  private EntityService service;

  @Autowired
  public EntityResource(final EntityService service) {
    this.service = service;
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve todos los resultados", response = Entity.class, responseContainer = "List<Entity>")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "numeroPagina", required = true, value = "Numero pagina a mostrar"),
      @ApiImplicitParam(name = "tamanoPagina", required = true, value = "Elementos por pagina a mostrar")
  })
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping
  public Mono<ServerResponse> findAll() {
    log.debug("Turns the flux into a Mono<List<T>> to allow sending a single response");
    Flux<Entity> all = this.service.findAll();
    return all.collectList().flatMap(
        (json) -> {
          if (json.isEmpty()) {
            throw new AppNotFoundException();
          } else {
            return ServerResponse.ok().contentType(APPLICATION_JSON).body(all, Entity.class);
          }
        }
    );
  }

}

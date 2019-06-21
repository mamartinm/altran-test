package com.altran.mamartin.webcontroller.resources;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.utils.Constants;
import com.altran.mamartin.model.services.EntityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
@RequestMapping(Constants.ENTITY)
public class EntityResource {


  private EntityService entityService;

  @Autowired
  public EntityResource(final EntityService entityService) {
    this.entityService = entityService;
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve todos los resultados", response = List.class, responseContainer = "List<Entity>")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping("/all")
  public DeferredResult<ResponseEntity<List<Entity>>> findAll() {
    log.debug("Empieza");
    DeferredResult<ResponseEntity<List<Entity>>> result = new DeferredResult<>();
    entityService.findAll()
        .whenCompleteAsync((entities, executor) -> {
              if (entities.isEmpty()) {
                result.setResult(ResponseEntity.notFound().build());
              } else {
                result.setResult(ResponseEntity.ok(entities));
              }
            }
        )
        .handleAsync((entities, throwable) ->
            result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
        );
    return result;
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve todos los resultados paginados", response = List.class, responseContainer = "List<Entity>")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", required = true, value = "Numero pagina a mostrar"),
      @ApiImplicitParam(name = "size", required = true, value = "Elementos por pagina a mostrar"),
      @ApiImplicitParam(name = "sort", required = false, value = "Campo por el que ordenar")
  })
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping
  public DeferredResult<ResponseEntity<Page<Entity>>> findAllPaginated(Pageable page) {
    log.debug("paso");
    DeferredResult<ResponseEntity<Page<Entity>>> result = new DeferredResult<>();
    entityService.findAllPaginated(page)
        .whenCompleteAsync((pageResult, executor) -> {
              if (pageResult.isEmpty()) {
                result.setResult(ResponseEntity.notFound().build());
              } else {
                result.setResult(ResponseEntity.ok(pageResult));
              }
            }
        )
        .handleAsync((entities, throwable) ->
            result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
        );
    return result;
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve un unico resultado", response = Entity.class, responseContainer = "Entity")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "id", required = true, value = "Id")
  })
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping("/{id}")
  public DeferredResult<ResponseEntity<Entity>> findById(@PathVariable("id") String id) {
    log.debug("paso");
    DeferredResult<ResponseEntity<Entity>> result = new DeferredResult<>();
    entityService.findById(id)
        .whenCompleteAsync((entity, executor) -> {
              if (entity.isEmpty()) {
                result.setResult(ResponseEntity.notFound().build());
              } else {
                result.setResult(ResponseEntity.ok(entity.get()));
              }
            }
        )
        .handleAsync((entity, throwable) ->
            result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
        );
    return result;
  }
}

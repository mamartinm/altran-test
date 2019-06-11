package com.altran.mamartin.webcontroller.resources;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.utils.Constants;
import com.altran.mamartin.model.services.EntityService;
import com.altran.mamartin.webcontroller.exceptions.AppNotFoundException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<List<Entity>> findAll() {
    log.debug("paso");
    List<Entity> all = entityService.findAll();
    if (all.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(all);
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve todos los resultados paginados", response = List.class, responseContainer = "List<Entity>")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", required = true, value = "Numero pagina a mostrar"),
      @ApiImplicitParam(name = "size", required = true, value = "Elementos por pagina a mostrar"),
      @ApiImplicitParam(name = "sort", required = false, value = "Campo por el que ordenar")
  })
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping
  public ResponseEntity<Page<Entity>> findAllPaginated(Pageable page) {
    log.debug("paso");
    Page<Entity> all = entityService.findAllPaginated(page);
    if (all.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(all);
    }
  }

  @ApiOperation(httpMethod = "GET", value = "Método que devuelve un unico resultado", response = Entity.class, responseContainer = "Entity")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "id", required = true, value = "Id")
  })
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "No existen resultados")})
  @GetMapping("/{id}")
  public ResponseEntity<Entity> findById(@PathVariable("id") String id) {
    log.debug("paso");
    Optional<Entity> one = entityService.findById(id);
    if (one.isEmpty()) {
      throw new AppNotFoundException();
    } else {
      return ResponseEntity.ok(one.get());
    }
  }
}

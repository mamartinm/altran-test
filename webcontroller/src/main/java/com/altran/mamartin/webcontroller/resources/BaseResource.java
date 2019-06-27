package com.altran.mamartin.webcontroller.resources;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.model.services.BaseService;
import com.altran.mamartin.webcontroller.async.AsyncResult;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@Getter
public abstract class BaseResource<T, S extends BaseService> {

  protected S service;

  @Autowired
  private ThreadPoolTaskScheduler threadPool;

  public BaseResource(S service) {
    this.service = service;
  }

  protected DeferredResult<ResponseEntity<List<T>>> findAllWhenCompleteAsync(CompletableFuture<List<T>> all) {
    return new AsyncResult<ResponseEntity<List<T>>>().whenCompleteAsync(all, threadPool);
  }

  protected DeferredResult<ResponseEntity<Page<T>>> findAllPaginatedWhenCompleteAsync(CompletableFuture<Page<T>> paginated) {
    return new AsyncResult<ResponseEntity<Page<T>>>().whenCompleteAsync(paginated, threadPool);
  }

  protected DeferredResult<ResponseEntity<Entity>> findByIdWhenCompleteAsync(CompletableFuture<Optional<T>> byId) {
    return new AsyncResult<ResponseEntity<Entity>>().whenCompleteAsync(byId, threadPool);
  }

}

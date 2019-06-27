package com.altran.mamartin.webcontroller.async;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
public class AsyncResult<T extends ResponseEntity<?>> extends DeferredResult {

  /**
   * Valor del timeout dentro del bloque asincrono del CompletableFuture
   */
  private static final Long DEFAULT_MODEL_TIME_OUT = 3000L;
  /**
   * Valor del timeout dentro del bloque asincrono del AsyncResult/DeferredResult
   */
  private static final Long DEFAULT_CONTROLLER_TIME_OUT = 1000L;

  public AsyncResult() {
    this(DEFAULT_CONTROLLER_TIME_OUT);
  }

  public AsyncResult(final Long timeout) {
    super(timeout);
  }

  public AsyncResult<T> whenCompleteAsync(final CompletableFuture<?> future, final ThreadPoolTaskScheduler threadPool) {
    future.
        whenCompleteAsync((result, executor) -> {
          log.debug("Empieza async");
          if (result == null) {
            throw new IllegalArgumentException("result es nulo");
          }
          if (result instanceof Collection && ((Collection) result).isEmpty()) {
            this.setResult(ResponseEntity.notFound().build());
          } else if (result instanceof Optional) {
            Optional optional = (Optional) result;
            this.setResult(optional.isPresent() ? ResponseEntity.ok(optional.get()) : ResponseEntity.notFound().build());
          } else {
            this.setResult(ResponseEntity.ok(result));
          }
          log.debug("Termina async");
        })
        .handleAsync((res, throwable) -> {
          if (throwable != null) {
            if (throwable instanceof IllegalArgumentException) {
              this.setErrorResult(ResponseEntity.notFound());
            } else {
              log.error("Ha ocurrido un error en el tratamiento del AsyncResult", throwable);
              this.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            }
          }
          return this;
        })
        .acceptEither(timeoutAfter(threadPool), result ->
            this.setResult(ResponseEntity.ok(result))
        );
    return this;
  }

  private <T> CompletableFuture<T> timeoutAfter(final ThreadPoolTaskScheduler threadPool) {
    CompletableFuture<T> result = new CompletableFuture<T>();
    threadPool.getScheduledThreadPoolExecutor().schedule(() ->
        result.completeExceptionally(new TimeoutException()), AsyncResult.DEFAULT_MODEL_TIME_OUT, TimeUnit.MILLISECONDS
    );
    return result;
  }
}

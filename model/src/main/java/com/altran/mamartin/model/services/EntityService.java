package com.altran.mamartin.model.services;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.model.repository.EntityRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntityService extends BaseService<Entity> {

  private static final int TIME_TO_RELOAD_CACHE = 30000;
  private static final String ENTITY_FIND_ALL = "entity_findAll";

  private CacheManager cacheManager;
  private EntityRepository repository;

  @Autowired
  public EntityService(final CacheManager cacheManager, final EntityRepository repository) {
    this.cacheManager = cacheManager;
    this.repository = repository;
  }

  @Async
  public CompletableFuture<List<Entity>> findAll() {
    return CompletableFuture.completedFuture(applicationContext.getBean(EntityService.class).createEntitiesFromDataSource());
  }

  @Async
  public CompletableFuture<Page<Entity>> findAllPaginated(Pageable pageable) {
    List<Entity> entities = applicationContext.getBean(EntityService.class).createEntitiesFromDataSource();
    return CompletableFuture.completedFuture(getPagination(pageable, entities));
  }

  @Async
  public CompletableFuture<Optional<Entity>> findById(String id) {
    List<Entity> entitiesFromDataSource = applicationContext.getBean(EntityService.class).createEntitiesFromDataSource();
    Optional<Entity> byId = entitiesFromDataSource.stream()
        .filter(entity -> entity.getId().equals(id))
        .findAny();
    return CompletableFuture.completedFuture(byId);
  }

  @Cacheable(ENTITY_FIND_ALL)
  public List<Entity> createEntitiesFromDataSource() {
    log.debug("Se crea cache");
    return repository.createEntitiesFromDataSource();
  }

  @Scheduled(fixedRate = TIME_TO_RELOAD_CACHE)
  public void evictEntityCacheAtIntervals() {
    log.debug("Se elimina cache cada " + TIME_TO_RELOAD_CACHE + " ms");
    Objects.requireNonNull(cacheManager.getCache(ENTITY_FIND_ALL)).clear();
  }

}

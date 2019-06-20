package com.altran.mamartin.model.services;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EntityService extends BaseService<Entity> {

  // @formatter:off
  private static final TypeReference<List<Entity>> TO_VALUE_TYPE_REF = new TypeReference<>() {  };
  // @formatter:on
  private static final int TIME_TO_RELOAD_CACHE = 30000;
  private static final String ENTITY_FIND_ALL = "entity_findAll";
  private static final String RESULT = "result";
  private static final String RESULTS = "results";
  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;
  private CacheManager cacheManager;

  @Value("${app.urls.data}")
  private String urlData;

  @Autowired
  public EntityService(final RestTemplate restTemplate, final ObjectMapper objectMapper, final CacheManager cacheManager) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.cacheManager = cacheManager;
  }

  @Cacheable(ENTITY_FIND_ALL)
  public Flux<Entity> findAll() {
    log.debug("Se crea cache");
    Mono<Map> resultWebClient = WebClient.create(urlData).get().retrieve().bodyToMono(Map.class);
    return resultWebClient.map(o -> {
      Object mapResult = ((Map) o.get(RESULT)).get(RESULTS);
      List<Entity> listEntities = objectMapper.convertValue(mapResult, TO_VALUE_TYPE_REF);
      for (Entity entity : listEntities) {
        entity.setUri(getUriBuilder(Constants.ENTITY).path(entity.getId()).build().encode().toUriString());
      }
      return listEntities;
    }).flatMapMany(Flux::fromIterable);
  }

  public Mono<Page> findAllPaginated(Pageable pageable) {
    return applicationContext.getBean(EntityService.class).findAll()
        .collectList()
        .flatMap(tuple -> {
          Page<Entity> pagination = getPagination(pageable, tuple);
          return Mono.just(pagination);
        });
  }

  private Mono<Page<Entity>> getJust(Pageable pageable, List<Entity> result) {
    return Mono.just(getPagination(pageable, result));
  }

  public Mono<Entity> findById(String id) {
    return applicationContext.getBean(EntityService.class).findAll()
        .filter(entity -> entity.getId().equals(id))
        .next();
  }

  @Scheduled(fixedRate = TIME_TO_RELOAD_CACHE)
  public void evictEntityCacheAtIntervals() {
    log.debug("Se elimina cache cada " + TIME_TO_RELOAD_CACHE + " ms");
    Objects.requireNonNull(cacheManager.getCache(ENTITY_FIND_ALL)).clear();
  }

}

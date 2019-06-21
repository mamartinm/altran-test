package com.altran.mamartin.model.repository;

import static org.springframework.security.config.Elements.HTTP;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class EntityRepository {

  // @formatter:off
  private static final TypeReference<List<Entity>> TO_VALUE_TYPE_REF = new TypeReference<>() {  };
  // @formatter:on
  private static final String RESULT = "result";
  private static final String RESULTS = "results";

  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  @Value("${app.server.hostname}")
  private String hostname;

  @Value("${server.port}")
  private String port;

  @Value("${server.servlet.context-path}")
  private String path;

  @Value("${app.urls.data}")
  private String urlData;

  @Autowired
  public EntityRepository(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  public List<Entity> createEntitiesFromDataSource() {
    List<Map> result = (List<Map>) ((Map) restTemplate.getForObject(urlData, Map.class).get(RESULT)).get(RESULTS);
    List<Entity> entities = objectMapper.convertValue(result, TO_VALUE_TYPE_REF);
    for (Entity entity : entities) {
      entity.setUri(getUriBuilder().path(entity.getId()).build().encode().toUriString());
    }
    return entities;
  }

  public CompletableFuture<List<Entity>> createFutureEntitiesFromDataSource() {
    return CompletableFuture.completedFuture(createEntitiesFromDataSource());
  }

  private UriComponentsBuilder getUriBuilder() {
    return UriComponentsBuilder.newInstance().scheme(HTTP).host(hostname).port(port).path(path).path(Constants.ENTITY).path(Constants.SLASH);
  }
}

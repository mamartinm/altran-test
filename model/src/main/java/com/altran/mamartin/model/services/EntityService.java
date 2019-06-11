package com.altran.mamartin.model.services;

import com.altran.mamartin.beans.dto.Entity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EntityService extends BaseService<Entity> {

  private static final TypeReference<List<Entity>> TO_VALUE_TYPE_REF = new TypeReference<>() {
  };
  private static final String RESULT = "result";
  private static final String RESULTS = "results";

  private RestTemplate restTemplate;
  private ObjectMapper objectMapper;

  @Value("${app.urls.data}")
  private String urlData;

  @Autowired
  public EntityService(final RestTemplate restTemplate, ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  public List<Entity> findAll() {
    List<Map> result = (List<Map>) ((Map) restTemplate.getForObject(urlData, Map.class).get(RESULT)).get(RESULTS);
    return objectMapper.convertValue(result, TO_VALUE_TYPE_REF);
  }

  public Page<Entity> findAllPaginated(Pageable pageable) {
    return getPagination(pageable, findAll());
  }

}

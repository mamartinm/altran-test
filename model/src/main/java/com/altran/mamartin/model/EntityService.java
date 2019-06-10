package com.altran.mamartin.model;

import com.altran.mamartin.beans.dto.Entity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class EntityService {


  public Flux<Entity> findAll() {
    WebClient WEB_CLIENT = WebClient.create("https://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search");
    return WEB_CLIENT.get().retrieve().bodyToFlux(Entity.class);
  }

}

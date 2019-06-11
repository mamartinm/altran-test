package com.altran.mamartin.model.config;

import static com.altran.mamartin.beans.utils.Constants.TIMEOUT;

import com.altran.mamartin.beans.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ModelConfig {

  @Bean
  public RestTemplate restTemplate() {
    RestTemplateBuilder builder = new RestTemplateBuilder();
    builder.setReadTimeout(Duration.ofSeconds(TIMEOUT));
    builder.setConnectTimeout(Duration.ofSeconds(TIMEOUT));
    return builder.build();
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.setSerializationInclusion(Include.NON_ABSENT);
    objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.setDateFormat(Constants.SDF.withColonInTimeZone(Boolean.TRUE));
    return objectMapper;
  }

}

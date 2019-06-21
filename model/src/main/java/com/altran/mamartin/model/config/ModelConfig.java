package com.altran.mamartin.model.config;

import static com.altran.mamartin.beans.utils.Constants.TIMEOUT;

import com.altran.mamartin.beans.utils.Constants;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.Duration;
import java.util.concurrent.Executor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
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

  @Bean
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(3);
    executor.setMaxPoolSize(3);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("AsynchThread-");
    executor.initialize();
    return executor;
  }

}

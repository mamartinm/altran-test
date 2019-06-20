package com.altran.mamartin.webcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableWebFlux
@ComponentScan(basePackages = "com.altran.mamartin")
public class Application {

  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    SpringApplication.run(Application.class, args);
  }

}

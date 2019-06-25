package com.altran.mamartin.webcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altran.mamartin")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

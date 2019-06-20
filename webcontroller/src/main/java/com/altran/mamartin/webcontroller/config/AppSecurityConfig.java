package com.altran.mamartin.webcontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class AppSecurityConfig {

  private static final String[] IGNORE_PATHS = {
      "/entity/**",
      "/v2/api-docs",
      "/swagger-ui.html",
      "/swagger.json",
      "/swagger-resources/**",
      "/configuration/security/**",
      "/configuration/ui",
      "/webjars/**",
      "/actuator/**",
      "/favicon.ico"
  };

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .pathMatchers(IGNORE_PATHS).permitAll()
        .anyExchange().authenticated()
        .and().build();
  }
}

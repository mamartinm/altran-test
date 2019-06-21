package com.altran.mamartin.webcontroller.config;

import com.altran.mamartin.beans.utils.Constants;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;

@Configuration
public class AppRestConfig {

  private static final String ALLOWED_METHODS = "POST, PUT, GET, OPTIONS, DELETE";
  private static final long MAX_AGE = 3600;

  @Autowired
  private ServerProperties serverProperties;

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Collections.singletonList(Constants.ASTERISK));
    corsConfig.setMaxAge(MAX_AGE);
    corsConfig.addAllowedMethod(ALLOWED_METHODS);
    corsConfig.setAllowCredentials(Boolean.TRUE);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(Constants.PATH_PATTERN, corsConfig);
    return new CorsWebFilter(source);
  }

  @Bean
  public WebFilter contextPathWebFilter() {
    String contextPath = serverProperties.getServlet().getContextPath();
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      if (request.getURI().getPath().startsWith(contextPath)) {
        return chain.filter(
            exchange.mutate()
                .request(request.mutate().contextPath(contextPath).build())
                .build());
      }
      return chain.filter(exchange);
    };
  }

}

package com.altran.mamartin.webcontroller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

@Configuration
public class AppRestConfig {

  @Autowired
  private ServerProperties serverProperties;

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

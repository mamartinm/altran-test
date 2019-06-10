package com.altran.mamartin.webcontroller.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * The {@code AppMvcConfig} class represents...
 */
@Configuration
public class AppMvcConfig {

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter() {
      @Override
      protected boolean shouldLog(HttpServletRequest request) {
        return Boolean.TRUE;
      }
    };
    loggingFilter.setIncludeClientInfo(Boolean.FALSE);
    loggingFilter.setIncludeQueryString(Boolean.TRUE);
    loggingFilter.setIncludePayload(Boolean.FALSE);
    loggingFilter.setIncludeHeaders(Boolean.FALSE);
    return loggingFilter;
  }
}

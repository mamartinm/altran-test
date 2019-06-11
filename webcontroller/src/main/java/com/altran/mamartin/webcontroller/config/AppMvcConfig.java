package com.altran.mamartin.webcontroller.config;

import com.altran.mamartin.webcontroller.interceptors.LoggerWebInterceptor;
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
    LoggerWebInterceptor loggingFilter = new LoggerWebInterceptor();
    loggingFilter.setIncludeClientInfo(Boolean.FALSE);
    loggingFilter.setIncludeQueryString(Boolean.TRUE);
    loggingFilter.setIncludePayload(Boolean.FALSE);
    loggingFilter.setIncludeHeaders(Boolean.FALSE);
    return loggingFilter;
  }
}
package com.altran.mamartin.webcontroller.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

  private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

  private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

  private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

  private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

  private static final String VALUE_ALLOW_ALL = "*";

  private static final String VALUE_ALLOW_METHODS = "POST, PUT, GET, OPTIONS, DELETE";

  private static final String VALUE_MAX_AGE = "3600";

  private static final String VALUE_HEADERS = "*, X-Requested-With, Content-Type, Authorization, credential, X-XSRF-TOKEN, "
      + "Cache-Control, Pragma, Access-Control-Allow-Headers, Origin, Accept, Access-Cotrol-Request-Method, Access-Control-Request-Headers";

  private static final String OPTIONS = "OPTIONS";

  @Override
  public void init(final FilterConfig fc) {
  }

  @Override
  public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) resp;
    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, VALUE_ALLOW_ALL);
    response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, VALUE_ALLOW_METHODS);
    response.setHeader(ACCESS_CONTROL_MAX_AGE, VALUE_MAX_AGE);
    response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, VALUE_HEADERS);
    response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, VALUE_HEADERS);
    HttpServletRequest request = (HttpServletRequest) req;
    if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, resp);
    }
  }

  @Override
  public void destroy() {
  }

}
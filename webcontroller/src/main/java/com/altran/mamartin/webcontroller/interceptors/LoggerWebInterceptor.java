package com.altran.mamartin.webcontroller.interceptors;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * The {@code TimeRequestInterceptor} class represents...
 */
public class LoggerWebInterceptor extends CommonsRequestLoggingFilter {

  private static final String START_REQUEST_IN_MILES = "start_request_in_miles";
  private static final String TIME_ELAPSED = " --> Time Elapsed ";
  private static final String MILLISECONDS = " ms";

  @Override
  protected boolean shouldLog(final HttpServletRequest request) {
    return Boolean.TRUE;
  }

  @Override
  protected void beforeRequest(final HttpServletRequest request, final String message) {
    super.beforeRequest(request, message);
    request.setAttribute(START_REQUEST_IN_MILES, new Date().getTime());
  }

  @Override
  protected void afterRequest(final HttpServletRequest request, final String message) {
    long startTime = (long) request.getAttribute(START_REQUEST_IN_MILES);
    long elapsedTime = System.currentTimeMillis() - startTime;
    StringBuilder sb = new StringBuilder(message);
    if (StringUtils.isEmpty(message)) {
      sb.append("\n");
    }
    sb.append(TIME_ELAPSED).append(elapsedTime).append(MILLISECONDS);
    super.afterRequest(request, sb.toString());
  }
}

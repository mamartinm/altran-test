package com.altran.mamartin.webcontroller.aspect;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect extends AbstractMonitoringInterceptor {

  public PerformanceMonitorAspect() {
  }

  public PerformanceMonitorAspect(boolean useDynamicLogger) {
    setUseDynamicLogger(useDynamicLogger);
  }

  @Pointcut("execution(java.util.concurrent.CompletableFuture com.altran.mamartin.model.services..*.*(..))"
      + " || execution(org.springframework.web.context.request.async.DeferredResult com.altran.mamartin.webcontroller.resources..*.*(..))"
      + "")
  public void monitor() {
  }

  @Bean
  public Advisor performanceMonitorAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(PerformanceMonitorAspect.class.getName() + ".monitor()");
    return new DefaultPointcutAdvisor(pointcut, new PerformanceMonitorAspect(Boolean.TRUE));
  }

  @Override
  protected Object invokeUnderTrace(MethodInvocation invocation, Log log) throws Throwable {
    String method = createInvocationTraceName(invocation);
    long start = System.currentTimeMillis();
    //log.info("Method " + method + " execution started at:" + new Date());
    try {
      return invocation.proceed();
    } finally {
      long end = System.currentTimeMillis();
      long time = end - start;
      if (time > 2000) {
        log.warn("Execution " + method + " longer than 2 sg!... " + time + " ms");
      } else {
        log.info("End:" + method + " in " + time + " ms");
      }
    }
  }
}

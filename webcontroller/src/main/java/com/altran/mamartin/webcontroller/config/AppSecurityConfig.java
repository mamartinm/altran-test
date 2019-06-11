package com.altran.mamartin.webcontroller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] PERMIT_PATH = {"/entity/**"};
  private static final String[] IGNORE_PATHS = {"/v2/api-docs",
      "/swagger-ui.html",
      "/swagger.json",
      "/swagger-resources/**",
      "/configuration/security/**",
      "/configuration/ui",
      "/webjars/**",
      "/actuator/**"};

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("user")
        .password("password")
        .roles("USER")
        .and()
        .withUser("admin")
        .password("admin")
        .roles("USER", "ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers(PERMIT_PATH).permitAll()
        .anyRequest().authenticated();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(IGNORE_PATHS);
  }

}

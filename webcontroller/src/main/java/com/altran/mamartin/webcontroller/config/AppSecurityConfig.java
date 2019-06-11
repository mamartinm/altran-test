package com.altran.mamartin.webcontroller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] PERMIT_PATH = {"/actuator/**", "/entity/**"};

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

}

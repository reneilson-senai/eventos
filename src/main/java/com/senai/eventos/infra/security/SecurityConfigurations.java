package com.senai.eventos.infra.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Autowired
  private SecurityFilter securityFilter;

  @Autowired
  private AutenticacaoEntryPoint autenticacaoEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    return http
      .csrf(s -> s.disable())
      .sessionManagement(management ->
        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers(HttpMethod.POST, "/login", "/pessoas", "/refresh")
          .permitAll()
          .requestMatchers(
            "/v3/api-docs/*",
            "/v3/*",
            "/swagger-ui.html",
            "/swagger-ui/*"
          )
          .permitAll()
          .requestMatchers(HttpMethod.DELETE, "/empresas")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(
        securityFilter,
        UsernamePasswordAuthenticationFilter.class
      )
      .exceptionHandling(handling ->
        handling.authenticationEntryPoint(autenticacaoEntryPoint)
      )
      .build();
  }
}

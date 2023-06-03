package com.senai.eventos.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
@Order(-999)
public class LogFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws IOException, ServletException {
    
    ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

    // Execution request chain
    filterChain.doFilter(req, resp);
    
    // Get Cache
    byte[] responseBody = resp.getContentAsByteArray();
    byte[] requestBody = req.getContentAsByteArray();

    log.info("Request\n METHOD: {} | URI: {} | PORT: {}", req.getMethod(), req.getRequestURI(), req.getLocalPort());
    
    log.info("Response\n STATUS: {}\n BODY:{}", resp.getStatus(), new String(responseBody, StandardCharsets.UTF_8));
    log.info("Response BODY: {}", new String(requestBody, StandardCharsets.UTF_8));
    
    // Finally remember to respond to the client with the cached data.
    resp.copyBodyToResponse();
  }
}

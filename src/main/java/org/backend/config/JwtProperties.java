package org.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(String secretKey, @DefaultValue Token token) {
  public record Token(
      @DefaultValue("courses_app") String issuer,
      @DefaultValue("8h") Duration lifetime) {
  }
}

package org.backend.service;

import org.backend.config.JwtProperties;
import org.backend.model.Role;
import org.backend.model.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class JwtService {
  private final JwtProperties jwtProperties;
  private final JwtEncoder jwtEncoder;

  public JwtService(
      JwtProperties jwtProperties,
      JwtEncoder jwtEncoder) {
    this.jwtProperties = jwtProperties;
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(String username, Role role) {
    final var now = Instant.now();
    final var expiresAt = now.plus(jwtProperties.token().lifetime());
    final var header = JwsHeader.with(MacAlgorithm.HS256).build();
    final var claims = JwtClaimsSet.builder()
        .issuer(jwtProperties.token().issuer())
        .issuedAt(now)
        .expiresAt(expiresAt)
        .subject(username)
        .claim("scope", role.toGrantedAuthority().getAuthority())
        .build();
    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
  }

  public String generateToken(User user) {
    return generateToken(user.getUsername(), user.getRole());
  }
}

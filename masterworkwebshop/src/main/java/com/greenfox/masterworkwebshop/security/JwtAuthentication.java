package com.greenfox.masterworkwebshop.security;

import com.greenfox.masterworkwebshop.users.models.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthentication {

  private static final int EXPIRATION = 30 * 60 * 1000;

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("firstName", user.getFirstName());
    additionalInfo.put("lastName", user.getLastName());
    additionalInfo.put("email", user.getEmail());
    additionalInfo.put("roles", authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    return Jwts.builder()
        .setSubject(user.getUsername())
        .addClaims(additionalInfo)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}

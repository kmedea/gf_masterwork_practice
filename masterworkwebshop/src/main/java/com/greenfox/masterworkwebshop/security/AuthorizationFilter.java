package com.greenfox.masterworkwebshop.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

  @Value("${security.jwt.secret}")
  private String jwtSecret;
  private UserDetailsService userDetailsService;

  public AuthorizationFilter(
      UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().equals("/login") || request.getServletPath().equals("/register")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        String token = authorizationHeader.substring("Bearer ".length());
        try {
          Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
          setSecContext(claims);
          filterChain.doFilter(request, response);
        } catch (Exception e) {
          response.sendError(FORBIDDEN.value());
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }

  private void setSecContext(Claims claims) {
    String username = claims.getSubject();
    UserDetails user = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
        user,  null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}

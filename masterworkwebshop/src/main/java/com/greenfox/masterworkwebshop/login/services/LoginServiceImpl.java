package com.greenfox.masterworkwebshop.login.services;

import com.greenfox.masterworkwebshop.login.models.entities.LoginRequest;
import com.greenfox.masterworkwebshop.security.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl
    implements LoginService {

  private AuthenticationManager authenticationManager;
  private JwtAuthentication jwtAuthentication;

  @Autowired
  public LoginServiceImpl(
      AuthenticationManager authenticationManager,
      JwtAuthentication jwtAuthentication) {
    this.authenticationManager = authenticationManager;
    this.jwtAuthentication = jwtAuthentication;
  }

  @Override
  public String loginAuthenticateCreateToken(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
            loginRequest.getPassword()));
    return jwtAuthentication.generateToken(authentication);
  }
}

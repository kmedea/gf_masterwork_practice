package com.greenfox.masterworkwebshop.login.controllers;

import com.greenfox.masterworkwebshop.login.models.entities.LoginRequest;
import com.greenfox.masterworkwebshop.login.models.entities.LoginResponse;
import com.greenfox.masterworkwebshop.login.services.LoginService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginPlayer(@Valid @RequestBody LoginRequest loginRequest) {
    String jwt = loginService.loginAuthenticateCreateToken(loginRequest);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }
}

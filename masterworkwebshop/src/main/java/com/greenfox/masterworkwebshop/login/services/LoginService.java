package com.greenfox.masterworkwebshop.login.services;

import com.greenfox.masterworkwebshop.login.models.entities.LoginRequest;

public interface LoginService {

  String loginAuthenticateCreateToken(LoginRequest loginRequest);
}

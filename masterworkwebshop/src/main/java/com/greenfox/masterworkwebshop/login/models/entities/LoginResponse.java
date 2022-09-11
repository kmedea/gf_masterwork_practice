package com.greenfox.masterworkwebshop.login.models.entities;

public class LoginResponse {

  private String status;
  private String token;

  public LoginResponse(String token) {
    this.status = "ok";
    this.token = token;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

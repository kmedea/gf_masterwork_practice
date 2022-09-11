package com.greenfox.masterworkwebshop.login.models.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

  @NotBlank(message = "Username is required.")
  private String userName;
  @NotNull(message = "Password is required.")
  private String password;


  public LoginRequest(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

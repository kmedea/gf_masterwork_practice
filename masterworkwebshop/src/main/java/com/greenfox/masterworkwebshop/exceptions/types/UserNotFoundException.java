package com.greenfox.masterworkwebshop.exceptions.types;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("User not found.");
  }
}

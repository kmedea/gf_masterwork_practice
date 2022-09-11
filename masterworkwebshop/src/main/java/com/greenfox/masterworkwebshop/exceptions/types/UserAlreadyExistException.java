package com.greenfox.masterworkwebshop.exceptions.types;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException() {
    super("Username is already taken.");
  }
}

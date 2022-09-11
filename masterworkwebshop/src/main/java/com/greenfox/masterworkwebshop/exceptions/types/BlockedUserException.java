package com.greenfox.masterworkwebshop.exceptions.types;

public class BlockedUserException extends RuntimeException {

  public BlockedUserException() {
    super("User is blocked.");
  }
}

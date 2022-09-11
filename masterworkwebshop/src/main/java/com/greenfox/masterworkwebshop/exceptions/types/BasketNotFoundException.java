package com.greenfox.masterworkwebshop.exceptions.types;

public class BasketNotFoundException extends RuntimeException {

  public BasketNotFoundException() {
    super("Basket not found.");
  }
}

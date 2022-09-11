package com.greenfox.masterworkwebshop.exceptions.types;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException() {
    super("Order not found.");
  }
}

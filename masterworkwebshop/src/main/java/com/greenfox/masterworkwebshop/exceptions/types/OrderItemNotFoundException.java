package com.greenfox.masterworkwebshop.exceptions.types;

public class OrderItemNotFoundException extends RuntimeException {

  public OrderItemNotFoundException() {
    super("Order item not found.");
  }
}

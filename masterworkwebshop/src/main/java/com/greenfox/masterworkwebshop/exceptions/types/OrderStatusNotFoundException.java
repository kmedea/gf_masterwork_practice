package com.greenfox.masterworkwebshop.exceptions.types;

public class OrderStatusNotFoundException extends RuntimeException {

  public OrderStatusNotFoundException() {
    super("Order status not found.");
  }
}

package com.greenfox.masterworkwebshop.exceptions.types;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException() {
    super("Product not found.");
  }
}

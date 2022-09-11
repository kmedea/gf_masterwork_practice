package com.greenfox.masterworkwebshop.exceptions.types;

public class ProductInactiveException
    extends RuntimeException {

  public ProductInactiveException() {
    super("Product is inactive.");
  }
}

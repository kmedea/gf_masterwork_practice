package com.greenfox.masterworkwebshop.exceptions.types;

public class ProductCategoryNotFoundException extends RuntimeException {

  public ProductCategoryNotFoundException() {
    super("Product category not found.");
  }
}

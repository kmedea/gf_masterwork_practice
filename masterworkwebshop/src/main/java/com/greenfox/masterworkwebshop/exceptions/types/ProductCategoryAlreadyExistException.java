package com.greenfox.masterworkwebshop.exceptions.types;

public class ProductCategoryAlreadyExistException extends RuntimeException {

  public ProductCategoryAlreadyExistException() {
    super("Product category is already taken.");
  }
}

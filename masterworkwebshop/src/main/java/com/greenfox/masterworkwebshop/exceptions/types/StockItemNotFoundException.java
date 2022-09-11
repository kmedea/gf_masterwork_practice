package com.greenfox.masterworkwebshop.exceptions.types;

public class StockItemNotFoundException extends RuntimeException{

  public StockItemNotFoundException() {
    super("Stock item not found.");
  }
}

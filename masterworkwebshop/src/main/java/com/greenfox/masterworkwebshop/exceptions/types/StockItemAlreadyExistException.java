package com.greenfox.masterworkwebshop.exceptions.types;

public class StockItemAlreadyExistException extends RuntimeException{

  public StockItemAlreadyExistException() {
    super("Stock item already exist.");
  }
}

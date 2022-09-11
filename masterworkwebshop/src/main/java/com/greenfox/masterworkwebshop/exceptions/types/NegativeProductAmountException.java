package com.greenfox.masterworkwebshop.exceptions.types;

public class NegativeProductAmountException extends RuntimeException {

  public NegativeProductAmountException() {
    super("Amount must be positive number.");
  }
}



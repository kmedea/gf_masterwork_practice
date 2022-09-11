package com.greenfox.masterworkwebshop.stock.models.dtos;

import javax.validation.constraints.NotNull;

public class ChangeAmountDTO {

  @NotNull(message = "Amount must not be empty.")
  private Integer amount;

  public ChangeAmountDTO() {
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}

package com.greenfox.masterworkwebshop.baskets.models.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChangeAmountInBasketDTO {

  private Integer itemId;
  @NotNull(message = "Amount must not be null or empty.")
  private Integer amount;

  public ChangeAmountInBasketDTO() {
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}

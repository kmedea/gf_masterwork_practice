package com.greenfox.masterworkwebshop.orders.models.dtos;

public class DeleteItemFromOrderByAdminDTO {

  private Integer itemId;

  public DeleteItemFromOrderByAdminDTO() {
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }
}

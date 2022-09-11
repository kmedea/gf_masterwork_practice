package com.greenfox.masterworkwebshop.products.models.dtos;

import com.greenfox.masterworkwebshop.baskets.models.enums.BasketStatus;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import java.util.List;

public class BasketDTO {

  private Integer userId;
  private List<BasketItem> items;
  private int totalPrice;
  private BasketStatus basketStatus;

  public BasketDTO() {
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public List<BasketItem> getItems() {
    return items;
  }

  public void setItems(
      List<BasketItem> items) {
    this.items = items;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public BasketStatus getBasketStatus() {
    return basketStatus;
  }

  public void setBasketStatus(
      BasketStatus basketStatus) {
    this.basketStatus = basketStatus;
  }
}

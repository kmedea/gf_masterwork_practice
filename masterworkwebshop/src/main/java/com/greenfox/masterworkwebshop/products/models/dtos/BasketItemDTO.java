package com.greenfox.masterworkwebshop.products.models.dtos;

import javax.validation.constraints.NotNull;

public class BasketItemDTO {

  private Integer productId;
  private Integer price;
  @NotNull(message = "Quantity may not be empty")
  private Integer quantity;

  public BasketItemDTO() {
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}

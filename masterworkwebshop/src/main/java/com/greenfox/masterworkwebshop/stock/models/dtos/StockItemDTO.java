package com.greenfox.masterworkwebshop.stock.models.dtos;

import com.greenfox.masterworkwebshop.products.models.entities.Product;
import javax.validation.constraints.NotNull;

public class StockItemDTO {

  private Integer productId;
  @NotNull(message = "Quantity must not be null.")
  private Integer quantity;

  public StockItemDTO() {
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}

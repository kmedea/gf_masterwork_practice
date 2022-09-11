package com.greenfox.masterworkwebshop.products.models.dtos;

import com.greenfox.masterworkwebshop.products.models.enums.ProductStatus;
import java.util.ArrayList;
import java.util.List;

public class UpdateProductDTO {

  private String name;
  private ProductStatus status;
  private List<String> categories;

  public UpdateProductDTO() {
    categories = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductStatus getStatus() {
    return status;
  }

  public void setStatus(ProductStatus status) {
    this.status = status;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(
      List<String> categories) {
    this.categories = categories;
  }
}

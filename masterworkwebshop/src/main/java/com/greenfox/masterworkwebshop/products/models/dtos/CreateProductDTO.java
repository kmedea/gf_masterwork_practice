package com.greenfox.masterworkwebshop.products.models.dtos;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class CreateProductDTO {

  @NotBlank(message = "Product name is required.")
  private String name;
  private List<String> category;

  public CreateProductDTO() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getCategory() {
    return category;
  }

  public void setCategory(List<String> category) {
    this.category = category;
  }
}

package com.greenfox.masterworkwebshop.products.models.dtos;

import javax.validation.constraints.NotBlank;

public class CreateProductCategoryDTO {

  @NotBlank(message = "Name of category is required.")
  private String nameOfCategory;

  public CreateProductCategoryDTO() {
  }

  public String getNameOfCategory() {
    return nameOfCategory;
  }

  public void setNameOfCategory(String nameOfCategory) {
    this.nameOfCategory = nameOfCategory;
  }
}

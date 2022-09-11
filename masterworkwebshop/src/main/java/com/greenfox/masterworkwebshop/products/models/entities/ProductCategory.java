package com.greenfox.masterworkwebshop.products.models.entities;

import com.greenfox.masterworkwebshop.products.models.enums.Category;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "product_categories")
public class ProductCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated(EnumType.STRING)
  private Category name;
  @ManyToMany(mappedBy = "categories")
  private Set<Product> products;

  public ProductCategory() {
    products = new HashSet<>();
  }

  public ProductCategory(Category name) {
    this();
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Category getName() {
    return name;
  }

  public void setName(Category name) {
    this.name = name;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(
      Set<Product> products) {
    this.products = products;
  }
}

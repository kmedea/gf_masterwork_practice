package com.greenfox.masterworkwebshop.products.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfox.masterworkwebshop.products.models.enums.Category;
import com.greenfox.masterworkwebshop.products.models.enums.ProductStatus;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "products")
public class Product {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String code;
  private String name;
  @Enumerated(EnumType.STRING)
  private ProductStatus status;
  @ManyToMany()
  @JoinTable(
      name = "product_category",
      joinColumns = {@JoinColumn(name = "product_id")},
      inverseJoinColumns = {@JoinColumn(name = "product_category_id")}
  )
  @JsonIgnore
  private Set<ProductCategory> categories;
  @ManyToMany(mappedBy = "favouriteProducts")
  @JsonIgnore
  private Set<User> productFans;

  public Product() {
    this.categories = new HashSet<>();
    this.productFans = new HashSet<>();
    this.status = ProductStatus.ACTIVE;
  }

  public Product(String name, Category category) {
    this();
    this.code = name + new Random().nextInt(1000);
    this.name = name;
    categories.add(new ProductCategory(category));
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Set<ProductCategory> getCategories() {
    return categories;
  }

  public void setCategories(
      Set<ProductCategory> categories) {
    this.categories = categories;
  }

  public Set<User> getProductFans() {
    return productFans;
  }

  public void setProductFans(
      Set<User> productFans) {
    this.productFans = productFans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id)
        && Objects.equals(code, product.code)
        && Objects.equals(name, product.name)
        && status == product.status
        && Objects.equals(categories, product.categories)
        && Objects.equals(productFans, product.productFans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, name, status, categories, productFans);
  }
}

package com.greenfox.masterworkwebshop.users.models.dtos;

import com.greenfox.masterworkwebshop.products.models.entities.Product;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

  private Integer userId;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
  private String address;
  private Integer basketId;
  Set<Product> favouriteProducts = new HashSet<>();

  public UserDTO() {
  }

  public UserDTO(Integer userId, String firstName, String lastName, String userName,
                 String email, String address, Integer basketId,
                 Set<Product> favouriteProducts) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.address = address;
    this.basketId = basketId;
    this.favouriteProducts = favouriteProducts;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getBasketId() {
    return basketId;
  }

  public void setBasketId(Integer basketId) {
    this.basketId = basketId;
  }

  public Set<Product> getFavouriteProducts() {
    return favouriteProducts;
  }

  public void setFavouriteProducts(
      Set<Product> favouriteProducts) {
    this.favouriteProducts = favouriteProducts;
  }
}

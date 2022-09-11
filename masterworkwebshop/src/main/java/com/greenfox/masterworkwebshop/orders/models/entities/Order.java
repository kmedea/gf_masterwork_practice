package com.greenfox.masterworkwebshop.orders.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.orders.models.enums.OrderStatus;
import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne
  @JsonIdentityReference(alwaysAsId = true)
  @JsonProperty("basketId")
  private Basket basket;
  private Timestamp purchaseDate;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<OrderItem> items;
  private Integer totalPrice;
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  private boolean isPaid;
  private String shippingAddress;
  private Timestamp shippingDate;

  public Order() {
    this.items = new ArrayList<>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Basket getBasket() {
    return basket;
  }

  public void setBasket(Basket basket) {
    this.basket = basket;
  }

  public Timestamp getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Timestamp purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Timestamp getShippingDate() {
    return shippingDate;
  }

  public void setShippingDate(Timestamp shippingDate) {
    this.shippingDate = shippingDate;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }
}

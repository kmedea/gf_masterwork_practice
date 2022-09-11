package com.greenfox.masterworkwebshop.orders.models.dtos;

import com.greenfox.masterworkwebshop.orders.models.enums.OrderStatus;
import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import java.sql.Timestamp;
import java.util.List;

public class OrderDTO {

  private Integer orderId;
  private Timestamp purchaseDate;
  private List<OrderItem> items;
  private Integer totalPrice;
  private OrderStatus status;
  private Boolean isPaid;
  private String shippingAddress;
  private Timestamp shippingDate;

  public OrderDTO() {
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

  public void setItems(
      List<OrderItem> items) {
    this.items = items;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Boolean isPaid() {
    return isPaid;
  }

  public void setPaid(Boolean paid) {
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

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }
}

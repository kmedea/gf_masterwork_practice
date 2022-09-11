package com.greenfox.masterworkwebshop.baskets.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfox.masterworkwebshop.baskets.models.enums.BasketStatus;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.users.models.entities.User;
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

@Entity(name = "baskets")
public class Basket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToOne
  @JsonIdentityReference(alwaysAsId = true)
  @JsonProperty("userId")
  private User user;
  @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<BasketItem> items;
  private int totalPrice;
  @Enumerated(EnumType.STRING)
  private BasketStatus basketStatus;


  public Basket() {
    this.items = new ArrayList<>();
    this.basketStatus = BasketStatus.ACTIVE;
  }

  public Basket(User user) {
    this();
    this.user = user;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<BasketItem> getItems() {
    return items;
  }

  public void setItems(List<BasketItem> items) {
    this.items = items;
  }

  public int getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
  }

  public BasketStatus getBasketStatus() {
    return basketStatus;
  }

  public void setBasketStatus(BasketStatus basketStatus) {
    this.basketStatus = basketStatus;
  }
}

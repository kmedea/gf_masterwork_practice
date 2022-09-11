package com.greenfox.masterworkwebshop.orders.repositories;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.orders.models.entities.Order;
import com.greenfox.masterworkwebshop.orders.models.enums.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {

  Iterable<Order> findAllByBasket(Basket basket);

  Iterable<Order> findAllByBasketAndStatus(Basket basket, OrderStatus status);

}

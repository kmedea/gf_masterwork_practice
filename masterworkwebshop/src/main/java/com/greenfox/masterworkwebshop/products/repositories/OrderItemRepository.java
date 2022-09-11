package com.greenfox.masterworkwebshop.products.repositories;

import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

}

package com.greenfox.masterworkwebshop.products.repositories;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import org.springframework.data.repository.CrudRepository;

public interface BasketItemRepository extends CrudRepository<BasketItem, Integer> {

  void deleteAllByBasket(Basket basket);
}

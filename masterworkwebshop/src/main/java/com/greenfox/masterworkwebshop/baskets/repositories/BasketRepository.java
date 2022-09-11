package com.greenfox.masterworkwebshop.baskets.repositories;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepository extends CrudRepository<Basket, Integer> {

  Optional<Basket> findBasketByUser(User user);

  Optional<Basket> findBasketByUser_Id(Integer userId);
}

package com.greenfox.masterworkwebshop.baskets.services;

import com.greenfox.masterworkwebshop.baskets.models.dtos.ChangeAmountInBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.dtos.DeleteItemFromBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketItemDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;

public interface BasketService {

  Basket createBasket(User user);

  void deleteByUser(User user);

  Integer findBasketIdByUserId(Integer userId);

  Basket findBasketByUser(User user);

  Basket save(Basket basket);

  BasketDTO addItemToBasket(User user, BasketItemDTO basketItemDTO);

  BasketDTO changeAmount(User user, ChangeAmountInBasketDTO changeAmountInBasketDTO);

  BasketDTO deleteItemFromBasket(User user, DeleteItemFromBasketDTO deleteItemFromBasketDTO);

  BasketDTO flushBasket(User user);

  BasketDTO getBasket(User user);
}

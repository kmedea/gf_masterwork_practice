package com.greenfox.masterworkwebshop.baskets.controllers;

import com.greenfox.masterworkwebshop.baskets.models.dtos.ChangeAmountInBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.dtos.DeleteItemFromBasketDTO;
import com.greenfox.masterworkwebshop.baskets.services.BasketService;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketItemDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

  private BasketService basketService;

  public BasketController(
      BasketService basketService) {
    this.basketService = basketService;
  }

  @PostMapping("/basket")
  public ResponseEntity<BasketDTO> addToBasketWithQuality(Authentication auth,
                                                          @Valid @RequestBody
                                                              BasketItemDTO basketItemDTO) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(basketService.addItemToBasket(user, basketItemDTO));
  }

  @PutMapping("/basket")
  public ResponseEntity<BasketDTO> changeAmountInBasket(Authentication auth,
                                                        @Valid @RequestBody
                                                            ChangeAmountInBasketDTO changeAmountInBasketDTO) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(basketService.changeAmount(user, changeAmountInBasketDTO));
  }

  @DeleteMapping("/basket")
  public ResponseEntity<BasketDTO> deleteItemFromBasket(Authentication auth,
                                                        @RequestBody
                                                            DeleteItemFromBasketDTO deleteItemFromBasketDTO) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(basketService.deleteItemFromBasket(user, deleteItemFromBasketDTO));
  }

  @DeleteMapping("/basket/flush")
  public ResponseEntity<BasketDTO> flushBasket(Authentication auth) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(basketService.flushBasket(user));
  }

  @GetMapping("/basket")
  public ResponseEntity<BasketDTO> getBasket(Authentication auth) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(basketService.getBasket(user));
  }


}

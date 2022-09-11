package com.greenfox.masterworkwebshop.baskets.services;

import com.greenfox.masterworkwebshop.baskets.models.dtos.ChangeAmountInBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.dtos.DeleteItemFromBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.baskets.repositories.BasketRepository;
import com.greenfox.masterworkwebshop.exceptions.types.BasketNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.BlockedUserException;
import com.greenfox.masterworkwebshop.exceptions.types.NegativeProductAmountException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductInactiveException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductNotFoundException;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.BasketItemDTO;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.products.models.enums.ProductStatus;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.models.enums.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl
    implements BasketService {

  private final BasketRepository basketRepository;
  private final ProductService productService;

  @Autowired
  public BasketServiceImpl(
      BasketRepository basketRepository,
      ProductService productService) {
    this.basketRepository = basketRepository;
    this.productService = productService;
  }

  @Override
  public Basket createBasket(User user) {
    Basket basket = new Basket(user);
    return basketRepository.save(basket);
  }


  @Override
  public void deleteByUser(User user) {
    Basket basket = basketRepository.findBasketByUser(user)
        .orElseThrow(BasketNotFoundException::new);
    basketRepository.delete(basket);
  }

  @Override
  public Integer findBasketIdByUserId(Integer userId) {
    Basket basket = basketRepository.findBasketByUser_Id(userId)
        .orElseThrow(BasketNotFoundException::new);
    return basket.getId();
  }

  @Override
  public Basket findBasketByUser(User user) {
    return basketRepository.findBasketByUser(user).orElseThrow(BasketNotFoundException::new);
  }

  @Override
  public Basket save(Basket basket) {
    return basketRepository.save(basket);
  }

  @Override
  public BasketDTO addItemToBasket(User user, BasketItemDTO basketItemDTO) {
    if (user.getState().equals(UserState.BLOCKED))  {
      throw new BlockedUserException();
    }
    if (productService.findProductById(basketItemDTO.getProductId()).getStatus().equals(
        ProductStatus.INACTIVE)) {
      throw new ProductInactiveException();
    }
    Basket basket = findBasketByUser(user);
    BasketItem basketItem = new BasketItem();
    basketItem.setProduct(productService.findProductById(basketItemDTO.getProductId()));
    basketItem.setQuantity(basketItemDTO.getQuantity());
    basketItem.setPrice(basketItemDTO.getPrice());
    basketItem.setBasket(basket);
    productService.saveBasketItem(basketItem);
    basket.getItems().add(basketItem);
    setTotalPrice(basket);
    basketRepository.save(basket);
    return convert(basket);
  }

    @Override
    public BasketDTO changeAmount(User user, ChangeAmountInBasketDTO changeAmountInBasketDTO){
      if (changeAmountInBasketDTO.getAmount() < 0) {
        throw new NegativeProductAmountException();
      }
      Basket basket = findBasketByUser(user);
      basket.getItems().stream()
          .filter(p -> p.getId().equals(changeAmountInBasketDTO.getItemId()))
          .forEach(p -> p.setQuantity(changeAmountInBasketDTO.getAmount()));
      setTotalPrice(basket);
      basketRepository.save(basket);
      return convert(basket);
    }

  @Override
  public BasketDTO deleteItemFromBasket(User user, DeleteItemFromBasketDTO deleteItemFromBasketDTO) {
    Basket basket = findBasketByUser(user);
    BasketItem item = basket.getItems().stream()
        .filter(p -> p.getId().equals(deleteItemFromBasketDTO.getItemId()))
        .findFirst()
        .orElseThrow(ProductNotFoundException::new);
    basket.getItems().remove(item);
    setTotalPrice(basket);
    basketRepository.save(basket);
    productService.deleteBasketItem(item);
    return convert(basket);
  }

  @Override
  public BasketDTO flushBasket(User user) {
    Basket basket = findBasketByUser(user);
    basket.getItems().clear();
    setTotalPrice(basket);
    basketRepository.save(basket);
    productService.deleteAllBasketItems(basket);
    return convert(basket);
  }

  @Override
  public BasketDTO getBasket(User user) {
    return convert(findBasketByUser(user));
  }

  private BasketDTO convert(Basket basket) {
    BasketDTO basketDTO = new BasketDTO();
    basketDTO.setUserId(basket.getUser().getId());
    basketDTO.setItems(basket.getItems());
    basketDTO.setTotalPrice(basket.getTotalPrice());
    basketDTO.setBasketStatus(basket.getBasketStatus());
    return basketDTO;
  }

  private void setTotalPrice(Basket basket) {
    Integer totalPrice = basket.getItems().stream()
        .mapToInt(p -> p.getPrice() * p.getQuantity())
        .sum();
    basket.setTotalPrice(totalPrice);
  }

}

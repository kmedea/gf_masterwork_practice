package com.greenfox.masterworkwebshop.baskets.services;

import static org.junit.jupiter.api.Assertions.*;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.baskets.repositories.BasketRepository;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.models.enums.UserRole;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasketServiceImplTest {

  @Mock
  private BasketRepository basketRepository;

  @Mock
  private ProductService productService;

  @InjectMocks
  @Spy
  private BasketServiceImpl basketService;

  private User user;
  private Product product;
  private Basket basket;
  private BasketItem basketItem;


  @BeforeEach
  void setUp() {
    product = new Product();
    user = new User();
    Set<Product> favouriteProducts = new HashSet<>();

    user.setId(1);
    user.setFirstName("Jane");
    user.setLastName("Doe");
    user.setUserName("JaneD");
    user.setPassword("password");
    user.setEmail("janed@test.hu");
    user.setAddress("Budapest");
    user.setRole(UserRole.ROLE_USER);
    user.setEnabled(true);

  }

  @Test
  void createBasket() {
  }

  @Test
  void deleteByUser() {
  }

  @Test
  void findBasketIdByUserId() {
  }

  @Test
  void findBasketByUser() {
  }

  @Test
  void save() {
  }

  @Test
  void addItemToBasket() {
  }

  @Test
  void changeAmount() {
  }

  @Test
  void deleteItemFromBasket() {
  }

  @Test
  void flushBasket() {
  }

  @Test
  void getBasket() {
  }
}
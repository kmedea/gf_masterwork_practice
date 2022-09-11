package com.greenfox.masterworkwebshop.users.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.baskets.services.BasketServiceImpl;
import com.greenfox.masterworkwebshop.exceptions.types.UserAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.UserNotFoundException;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.services.ProductServiceImpl;
import com.greenfox.masterworkwebshop.users.models.dtos.RegistrationDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.models.enums.UserState;
import com.greenfox.masterworkwebshop.users.repositories.UserRepository;
import java.util.Optional;
import javax.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  BasketServiceImpl basketService;

  @Mock
  ProductServiceImpl productService;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserServiceImpl userService;

  User testUser;
  Basket testBasket;
  Product testProduct;
  RegistrationDTO testRegistrationDTO;


  @BeforeEach
  void setUp() {

    testRegistrationDTO = new RegistrationDTO();
    testRegistrationDTO.setFirstName("Jason");
    testRegistrationDTO.setLastName("Doe");
    testRegistrationDTO.setUserName("JasonD");
    testRegistrationDTO.setPassword("password");
    testRegistrationDTO.setEmail("jasond@test.hu");
    testRegistrationDTO.setAddress("Budapest");

    testUser = new User(testRegistrationDTO);
    testUser.setId(1);

    testBasket = new Basket(testUser);

    testProduct = new Product();
    testProduct.setId(1);
    testProduct.setName("asztal");
  }

  @Test
  void successfull_register() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).then(returnsFirstArg());
    when(basketService.createBasket(any(User.class))).thenReturn(new Basket());

    UserDTO actualUserDTO = userService.register(testRegistrationDTO);

    Assertions.assertNotNull(actualUserDTO);
    assertEquals(testUser.getUserName(), actualUserDTO.getUserName());
    assertEquals(testUser.getAddress(), actualUserDTO.getAddress());
  }

  @Test
  public void whenUserAlreadyExist_register() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(testUser));

    UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class,
        () -> userService.register(testRegistrationDTO));

    String expectedMessage = "Username is already taken.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void getUser() {
    when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

    User actualUser = userService.getUser(testUser.getId());

    Assertions.assertNotNull(actualUser);
    assertEquals(testUser.getUserName(), actualUser.getUserName());
    assertEquals(testUser.getId(), actualUser.getId());
  }

  @Test
  public void whenUserNotFound_getUser() {

    UserNotFoundException exception = assertThrows(UserNotFoundException.class,
        () -> userService.getUser(100));

    String expectedMessage = "User not found.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void successfull_blockUser() {

    when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
    when(basketService.findBasketByUser(testUser)).thenReturn(testBasket);
    when(userRepository.save(any(User.class))).then(returnsFirstArg());
    when(basketService.save(any(Basket.class))).then(returnsFirstArg());

    User actualUser = userService.blockUser(testUser.getId());

    Assertions.assertNotNull(actualUser);
    assertFalse(actualUser.getEnabled());
    assertEquals(actualUser.getState(), UserState.BLOCKED);
  }

  @Test
  void successfull_addFavouriteProduct() {

    when(productService.findProductById(anyInt())).thenReturn(testProduct);
    when(userRepository.save(any(User.class))).then(returnsFirstArg());

    UserDTO actualUserDTO = userService.addFavouriteProduct(testUser, testProduct.getId());

    Assertions.assertNotNull(actualUserDTO);
    assertTrue(actualUserDTO.getFavouriteProducts().contains(testProduct));
  }
}
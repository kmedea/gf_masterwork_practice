package com.greenfox.masterworkwebshop.users.services;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.baskets.models.enums.BasketStatus;
import com.greenfox.masterworkwebshop.baskets.services.BasketService;
import com.greenfox.masterworkwebshop.exceptions.types.UserAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.UserNotFoundException;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import com.greenfox.masterworkwebshop.users.models.dtos.RegistrationDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserUpdateDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.models.enums.UserState;
import com.greenfox.masterworkwebshop.users.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
    implements UserService {


  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final BasketService basketService;
  private final ProductService productService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         BasketService basketService,
                         ProductService productService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.basketService = basketService;
    this.productService = productService;
  }

  @Override
  public UserDTO register(RegistrationDTO registrationDTO) throws UserAlreadyExistException {
    if (userRepository.findByUserName(registrationDTO.getUserName()).isPresent()) {
      throw new UserAlreadyExistException();
    }
    User user = new User(registrationDTO);
    String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
    user.setPassword(encodedPassword);
    userRepository.save(user);
    Basket basket = basketService.createBasket(user);
    return convert(user);
  }

  @Override
  @Transactional
  public Integer deleteUser(Integer id) {
    User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    basketService.deleteByUser(user);
    return userRepository.deleteUserById(id);
  }

  @Override
  public List<UserDTO> getUsers() {
    return userRepository.findAll().stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  @Override
  public User getUser(Integer id) throws UserNotFoundException {
    User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    return user;
  }

  @Override
  public User blockUser(Integer id) {
    User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    user.setEnabled(false);
    user.setState(UserState.BLOCKED);
    Basket basket = basketService.findBasketByUser(user);
    basket.setBasketStatus(BasketStatus.BLOCKED);
    userRepository.save(user);
    basketService.save(basket);
    return user;
  }

  @Override
  public UserDTO updateUserDatas(Integer id, UserUpdateDTO userUpdateDTO) {
    User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    user.setFirstName(
        userUpdateDTO.getFirstName() == null ? user.getFirstName() : userUpdateDTO.getFirstName());
    user.setLastName(
        userUpdateDTO.getLastName() == null ? user.getLastName() : userUpdateDTO.getLastName());
    user.setEmail(userUpdateDTO.getEmail() == null ? user.getEmail() : userUpdateDTO.getEmail());
    user.setPassword(userUpdateDTO.getPassword() == null ? user.getPassword() : passwordEncoder
        .encode(userUpdateDTO.getPassword()));
    user.setAddress(
        userUpdateDTO.getAddress() == null ? user.getAddress() : userUpdateDTO.getAddress());
    user.setEnabled(
        userUpdateDTO.isEnabled() == null ? user.isEnabled() : userUpdateDTO.isEnabled());
    user.setState(userUpdateDTO.getState() == null ? user.getState() : userUpdateDTO.getState());
    user.setRole(userUpdateDTO.getRole() == null ? user.getRole() : userUpdateDTO.getRole());

    userRepository.save(user);
    return convert(user);
  }

  @Override
  public UserDTO addFavouriteProduct(User user, Integer productId) {
    Product product = productService.findProductById(productId);
    user.getFavouriteProducts().add(product);
    userRepository.save(user);
    return convert(user);
  }

  @Override
  public UserDTO deleteFavouriteProduct(User user, Integer productId) {
    Product product = productService.findProductById(productId);
    user.getFavouriteProducts().remove(product);
    userRepository.save(user);
    return convert(user);
  }

  private UserDTO convert(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setUserName(user.getUserName());
    userDTO.setEmail(user.getEmail());
    userDTO.setAddress(user.getAddress());
    userDTO.setBasketId(basketService.findBasketIdByUserId(user.getId()));
    userDTO.setFavouriteProducts(user.getFavouriteProducts());
    return userDTO;
  }

}

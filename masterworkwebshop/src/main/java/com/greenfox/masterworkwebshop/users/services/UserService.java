package com.greenfox.masterworkwebshop.users.services;

import com.greenfox.masterworkwebshop.users.models.dtos.RegistrationDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserUpdateDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.List;

public interface UserService {
  UserDTO register(RegistrationDTO registrationDTO);

  Integer deleteUser(Integer id);

  List<UserDTO> getUsers();

  User getUser(Integer id);

  User blockUser(Integer id);

  UserDTO updateUserDatas(Integer id, UserUpdateDTO userUpdateDTO);

  UserDTO addFavouriteProduct(User user, Integer productId);

  UserDTO deleteFavouriteProduct(User user, Integer productId);

}

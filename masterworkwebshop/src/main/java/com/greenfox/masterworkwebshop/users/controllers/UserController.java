package com.greenfox.masterworkwebshop.users.controllers;

import com.greenfox.masterworkwebshop.users.models.dtos.RegistrationDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserDTO;
import com.greenfox.masterworkwebshop.users.models.dtos.UserUpdateDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/registration")
  public ResponseEntity<UserDTO> register(@Valid @RequestBody RegistrationDTO registrationDTO) {
    UserDTO userDTO = userService.register(registrationDTO);
    return ResponseEntity.ok().body(userDTO);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/users")
  public ResponseEntity<?> listUsers(Authentication auth) {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUser(Authentication authentication, @PathVariable Integer id) {
    return ResponseEntity.ok().body(userService.getUser(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/user/{id}")
  public ResponseEntity<String> deleteUser(Authentication auth, @PathVariable Integer id) {
    userService.deleteUser(id);
    String message =
        String.format("A %d-as id-val rendelkező felhasználó törlése sikeres volt.", id);
    return ResponseEntity.ok().body(message);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/user/{id}/block")
  public ResponseEntity<User> blockUser(Authentication auth, @PathVariable Integer id) {
    return ResponseEntity.ok().body(userService.blockUser(id));
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<UserDTO> updateUserDatas(Authentication auth,
                                                 @PathVariable Integer id,
                                                 @RequestBody UserUpdateDTO updateUserDTO) {
    return ResponseEntity.ok().body(userService.updateUserDatas(id, updateUserDTO));
  }

  @PutMapping("/user/favourite/{productId}")
  public ResponseEntity<UserDTO> addFavouriteProductToUser(Authentication auth,
                                                 @PathVariable Integer productId) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(userService.addFavouriteProduct(user, productId));
  }

  @DeleteMapping("/user/favourite/{productId}")
  public ResponseEntity<UserDTO> deleteFavouriteProductToUser(Authentication auth,
                                                           @PathVariable Integer productId) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(userService.deleteFavouriteProduct(user, productId));

  }
}

package com.greenfox.masterworkwebshop.users.models.dtos;

import com.greenfox.masterworkwebshop.users.models.enums.UserRole;
import com.greenfox.masterworkwebshop.users.models.enums.UserState;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateDTO {

  private String firstName;
  private String lastName;
  private String email;
  @NotNull(message = "Password is required.")
  @Size(min = 3, message = "Password must be 8 characters.")
  private String password;
  private String address;
  private UserRole role;
  private UserState state;
  private Boolean enabled;

  public UserUpdateDTO() {
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public UserState getState() {
    return state;
  }

  public void setState(UserState state) {
    this.state = state;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}

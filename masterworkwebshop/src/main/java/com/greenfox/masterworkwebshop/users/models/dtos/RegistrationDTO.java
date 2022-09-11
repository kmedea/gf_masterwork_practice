package com.greenfox.masterworkwebshop.users.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationDTO {

  private String firstName;
  private String lastName;
  @NotBlank(message = "Username is required.")
  private String userName;
  @Email
  private String email;
  @NotNull(message = "Password is required.")
  @Size(min = 5, message = "Password must be 5 characters.")
  private String password;
  private String address;

  public RegistrationDTO() {
  }

  public RegistrationDTO(String firstName, String lastName, String userName, String email,
                         String password, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.address = address;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

package com.greenfox.masterworkwebshop.users.models.entities;

import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.users.models.dtos.RegistrationDTO;
import com.greenfox.masterworkwebshop.users.models.enums.UserRole;
import com.greenfox.masterworkwebshop.users.models.enums.UserState;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
  private String password;
  private String address;
  @Enumerated(EnumType.STRING)
  private UserRole role;
  @Enumerated(EnumType.STRING)
  private UserState state;
  private boolean enabled;
  @ManyToMany
  @JoinTable(
      name = "user_favourite_products",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "product_id")}
  )
  @LazyCollection(LazyCollectionOption.FALSE)
  Set<Product> favouriteProducts;

  public User() {
    favouriteProducts = new HashSet<>();
  }

  public User(RegistrationDTO registrationDTO) {
    this();
    this.firstName = registrationDTO.getFirstName();
    this.lastName = registrationDTO.getLastName();
    this.userName = registrationDTO.getUserName();
    this.password = registrationDTO.getPassword();
    this.email = registrationDTO.getEmail();
    this.address = registrationDTO.getAddress();
    this.state = UserState.ACTIVE;
    this.enabled = true;
    this.role = UserRole.ROLE_USER;
  }

  public User(Integer id, String firstName, String lastName, String userName, String email,
              String password, String address,
              UserRole role, UserState state, boolean enabled) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.address = address;
    this.role = role;
    this.state = state;
    this.enabled = enabled;
    this.favouriteProducts = new HashSet<>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public boolean getEnabled() {
    return this.state.name().equals("ACTIVE");
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<Product> getFavouriteProducts() {
    return favouriteProducts;
  }

  public void setFavouriteProducts(
      Set<Product> favouriteProducts) {
    this.favouriteProducts = favouriteProducts;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(role == null ? UserRole.ROLE_USER.getAuthority() : role.getAuthority());
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return getEnabled();
  }
}

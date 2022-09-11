package com.greenfox.masterworkwebshop.users.services;

import com.greenfox.masterworkwebshop.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailsService")
public class UserDetService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetService(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("No such user"));
  }
}

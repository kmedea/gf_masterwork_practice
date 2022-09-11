package com.greenfox.masterworkwebshop.users.repositories;

import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  @Override
  Optional<User> findById(Integer integer);

  Optional<User> findByUserName(String userName);

  Integer deleteUserById(Integer id);

  List<User> findAll();
}

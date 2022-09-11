package com.greenfox.masterworkwebshop.products.repositories;

import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.models.entities.ProductCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

  List<Product> findAll();

  List<Product> findAllByCategories(ProductCategory category);

  void deleteProductById(Integer productId);

  Optional<Product> findProductById(Integer id);

}

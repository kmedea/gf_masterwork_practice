package com.greenfox.masterworkwebshop.products.repositories;

import com.greenfox.masterworkwebshop.products.models.entities.ProductCategory;
import com.greenfox.masterworkwebshop.products.models.enums.Category;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

  Optional<ProductCategory> findProductCategoryByName(Category category);
}

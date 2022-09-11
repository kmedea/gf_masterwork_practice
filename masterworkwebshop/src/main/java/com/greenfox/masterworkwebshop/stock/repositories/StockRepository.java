package com.greenfox.masterworkwebshop.stock.repositories;

import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.stock.models.entities.StockItem;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<StockItem, Integer> {

  List<StockItem> findAll();
  StockItem findStockItemByProduct(Product product);
}

package com.greenfox.masterworkwebshop.stock.services;

import com.greenfox.masterworkwebshop.stock.models.dtos.ChangeAmountDTO;
import com.greenfox.masterworkwebshop.stock.models.dtos.StockItemDTO;
import com.greenfox.masterworkwebshop.stock.models.entities.StockItem;
import java.util.List;

public interface StockService {
  StockItem addNewItemToStock(StockItemDTO stockItemDTO);

  StockItem deleteItemFromStock(Integer id);

  StockItem changeAmount(Integer id, Integer amount);

  List<StockItem> getStock();

  StockItem getStockItem(Integer id);
}

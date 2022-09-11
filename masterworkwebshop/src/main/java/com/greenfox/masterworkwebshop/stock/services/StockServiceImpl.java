package com.greenfox.masterworkwebshop.stock.services;

import com.greenfox.masterworkwebshop.exceptions.types.NegativeProductAmountException;
import com.greenfox.masterworkwebshop.exceptions.types.StockException;
import com.greenfox.masterworkwebshop.exceptions.types.StockItemNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.StockItemAlreadyExistException;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import com.greenfox.masterworkwebshop.stock.models.dtos.ChangeAmountDTO;
import com.greenfox.masterworkwebshop.stock.models.dtos.StockItemDTO;
import com.greenfox.masterworkwebshop.stock.models.entities.StockItem;
import com.greenfox.masterworkwebshop.stock.repositories.StockRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl
    implements StockService {

  private final StockRepository stockRepository;
  private final ProductService productService;

  public StockServiceImpl(
      StockRepository stockRepository,
      ProductService productService) {
    this.stockRepository = stockRepository;
    this.productService = productService;
  }

  @Override
  public StockItem addNewItemToStock(StockItemDTO stockItemDTO) {
    if (stockItemDTO.getQuantity() < 0) {
      throw new NegativeProductAmountException();
    }
    List<StockItem> items = stockRepository.findAll();
    Boolean findAny = items.stream()
        .map(si -> si.getProduct().getId().equals(stockItemDTO.getProductId()))
        .noneMatch(bo -> bo.equals(true));
    if (!findAny) {
      throw new StockItemAlreadyExistException();
    }
    StockItem stockItem = new StockItem();
    stockItem.setProduct(productService.findProductById(stockItemDTO.getProductId()));
    stockItem.setQuantity(stockItemDTO.getQuantity());
    return stockRepository.save(stockItem);
  }

  @Override
  public StockItem deleteItemFromStock(Integer id) {
    StockItem stockItem = stockRepository.findById(id).orElseThrow(StockItemNotFoundException::new);
    stockRepository.delete(stockItem);
    return stockItem;
  }

  @Override
  public StockItem changeAmount(Integer id, Integer amount) {
    Product product = productService.findProductById(id);
    StockItem stockItem = stockRepository.findStockItemByProduct(product);
    Integer newAmount = stockItem.getQuantity()+amount;
    if(amount<0) {
      if (newAmount < 0) {
        throw new StockException("Stock item is not enough.");
      } else {
        stockItem.setQuantity(newAmount);
      }
    }
    stockItem.setQuantity(newAmount);
    stockRepository.save(stockItem);
    return stockItem;
  }

  @Override
  public List<StockItem> getStock() {
    return stockRepository.findAll();
  }

  @Override
  public StockItem getStockItem(Integer id) {
    return stockRepository.findById(id).orElseThrow(StockItemNotFoundException::new);
  }
}

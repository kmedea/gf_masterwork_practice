package com.greenfox.masterworkwebshop.stock.controllers;

import com.greenfox.masterworkwebshop.stock.models.dtos.ChangeAmountDTO;
import com.greenfox.masterworkwebshop.stock.models.dtos.StockItemDTO;
import com.greenfox.masterworkwebshop.stock.models.entities.StockItem;
import com.greenfox.masterworkwebshop.stock.services.StockService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/stock")
  public ResponseEntity<StockItem> addItemToStock(Authentication auth,
                                                  @Valid @RequestBody StockItemDTO stockItemDTO) {
    return ResponseEntity.ok().body(stockService.addNewItemToStock(stockItemDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/stock/{id}")
  public ResponseEntity<StockItem> deleteItemFromStock(Authentication auth,
                                                       @PathVariable Integer id) {
    return ResponseEntity.ok().body(stockService.deleteItemFromStock(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/stock/{id}")
  public ResponseEntity<StockItem> changeItemAmount(Authentication auth,
                                                    @PathVariable Integer id,
                                                    @Valid @RequestBody
                                                        ChangeAmountDTO changeAmountDTO) {
    return ResponseEntity.ok().body(stockService.changeAmount(id, changeAmountDTO.getAmount()));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/stock")
  public ResponseEntity<List<StockItem>> getStock(Authentication auth) {
    return ResponseEntity.ok().body(stockService.getStock());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/stock/{id}")
  public ResponseEntity<StockItem> getStockItem(Authentication auth,
                                                      @PathVariable Integer id) {
    return ResponseEntity.ok().body(stockService.getStockItem(id));
  }
}

package com.greenfox.masterworkwebshop.orders.controllers;

import com.greenfox.masterworkwebshop.baskets.models.dtos.DeleteItemFromBasketDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.DeleteItemFromOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.ListAllOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.OrderDTO;
import com.greenfox.masterworkwebshop.orders.services.OrderService;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/order")
  public ResponseEntity<OrderDTO> createOrder(Authentication auth) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(orderService.createOrder(user));
  }

  @GetMapping("/order")
  public ResponseEntity<List<OrderDTO>> listAllOrdersByUser(Authentication auth,
                                                      @RequestParam(required = false)
                                                          String orderStatus) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(orderService.listAllOrder(user, orderStatus));
  }


  @GetMapping("/order/{orderId}")
  public ResponseEntity<OrderDTO> getOrder(Authentication auth,
                                                 @PathVariable Integer orderId) {
    return ResponseEntity.ok().body(orderService.getOrder(orderId));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/orders")
  public ResponseEntity<List<OrderDTO>> listAllOrdersByAdmin(Authentication auth,
                                                             @RequestParam(required = false)
                                                                 String orderStatus,
                                                             @RequestBody
                                                             ListAllOrderByAdminDTO listAllOrderByAdminDTO) {
    User user = (User) auth.getPrincipal();
    return ResponseEntity.ok().body(orderService.listAllOrderByAdmin(orderStatus, listAllOrderByAdminDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/orders/{orderId}")
  public ResponseEntity<String> finalDeleteOrderByAdmin(Authentication auth,
                                                        @PathVariable Integer orderId) {
    orderService.finalDeleteOrderByAdmin(orderId);
    String response = String.format("A %d id-val rendelkező rendelés törölve lett.", orderId);
    return ResponseEntity.ok().body(response);
  }

    @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/order/{orderId}")
  public ResponseEntity<OrderDTO> deleteItemFromOrder(Authentication auth,
                                                      @PathVariable Integer orderId,
                                                      @RequestBody
                                                      DeleteItemFromOrderByAdminDTO deleteItemFromOrderByAdminDTO) {
    return ResponseEntity.ok().body(orderService.deleteItemFromOrder(orderId, deleteItemFromOrderByAdminDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/order/{orderId}")
  public ResponseEntity<OrderDTO> changeOrderStatus(Authentication auth,
                                                    @PathVariable Integer orderId,
                                                    @RequestParam(required = false) String status) {
    return ResponseEntity.ok().body(orderService.changeOrderStatus(orderId, status));
  }


}

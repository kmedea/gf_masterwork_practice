package com.greenfox.masterworkwebshop.orders.services;

import com.greenfox.masterworkwebshop.orders.models.dtos.DeleteItemFromOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.ListAllOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.OrderDTO;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.List;

public interface OrderService {
  OrderDTO createOrder(User user);

  List<OrderDTO> listAllOrder(User user, String orderStatus);

  List<OrderDTO> listAllOrderByAdmin(String orderStatus, ListAllOrderByAdminDTO listAllOrderByAdminDTO);

  Integer finalDeleteOrderByAdmin(Integer orderId);

  OrderDTO changeOrderStatus(Integer orderId, String status);

  OrderDTO deleteItemFromOrder(Integer orderId, DeleteItemFromOrderByAdminDTO deleteItemFromOrderByAdminDTO);

  OrderDTO getOrder(Integer orderId);
}

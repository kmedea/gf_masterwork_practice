package com.greenfox.masterworkwebshop.orders.services;

import com.greenfox.masterworkwebshop.baskets.models.dtos.ChangeAmountInBasketDTO;
import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.baskets.services.BasketService;
import com.greenfox.masterworkwebshop.exceptions.types.OrderItemNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.OrderNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.OrderStatusNotFoundException;
import com.greenfox.masterworkwebshop.orders.models.dtos.DeleteItemFromOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.ListAllOrderByAdminDTO;
import com.greenfox.masterworkwebshop.orders.models.dtos.OrderDTO;
import com.greenfox.masterworkwebshop.orders.models.entities.Order;
import com.greenfox.masterworkwebshop.orders.models.enums.OrderStatus;
import com.greenfox.masterworkwebshop.orders.repositories.OrderRepository;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import com.greenfox.masterworkwebshop.stock.models.dtos.ChangeAmountDTO;
import com.greenfox.masterworkwebshop.stock.models.entities.StockItem;
import com.greenfox.masterworkwebshop.stock.services.StockService;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import com.greenfox.masterworkwebshop.users.services.UserService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl
    implements OrderService {

  private final OrderRepository orderRepository;
  private final BasketService basketService;
  private final ProductService productService;
  private final UserService userService;
  private final StockService stockService;

  @Autowired
  public OrderServiceImpl(
      OrderRepository orderRepository,
      BasketService basketService,
      ProductService productService,
      UserService userService,
      StockService stockService) {
    this.orderRepository = orderRepository;
    this.basketService = basketService;
    this.productService = productService;
    this.userService = userService;
    this.stockService = stockService;
  }

  @Override
  public OrderDTO createOrder(User user) {
    Basket basket = basketService.findBasketByUser(user);
    basket.getItems()
        .forEach(basketItem -> stockService.changeAmount(basketItem.getProduct().getId(), -basketItem.getQuantity()));
    Order order = new Order();
    List<OrderItem> orderItems = basket.getItems().stream()
        .map(p -> convertBasketItemToOrderItem(p, order))
        .collect(Collectors.toList());
    order.setBasket(basket);
    order.setItems(orderItems);
    setTotalPrice(order);
    order.setPurchaseDate(new Timestamp(System.currentTimeMillis() / 1000));
    order.setStatus(
        OrderStatus.ACTIVE);
    order.setPaid(true);
    order.setShippingAddress(user.getAddress());
    order.setShippingDate(new Timestamp(System.currentTimeMillis() / 1000 + 86400)); //24 óra múlva
    Order savedOrder = orderRepository.save(order);
    productService.saveOrderItem(orderItems);
    basketService.flushBasket(user);
    return convert(order);
  }

  @Override
  public List<OrderDTO> listAllOrder(User user, String orderStatusName) {
    Iterable<Order> ordersIterable;
    Basket basket = basketService.findBasketByUser(user);
    if (orderStatusName == null) {
      ordersIterable = orderRepository.findAllByBasket(basket);
    } else {
      OrderStatus orderStatus = OrderStatus.valueOf(orderStatusName);
      ordersIterable = orderRepository.findAllByBasketAndStatus(basket, orderStatus);
    }
    List<Order> orderList = new ArrayList<>();
    ordersIterable.forEach(orderList::add);
    return orderList.stream()
        .map(this::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderDTO> listAllOrderByAdmin(String orderStatus,
                                            ListAllOrderByAdminDTO listAllOrderByAdminDTO) {
    User user = userService.getUser(listAllOrderByAdminDTO.getUserId());
    return listAllOrder(user, orderStatus);
  }

  @Override
  public Integer finalDeleteOrderByAdmin(Integer orderId) {
    orderRepository.deleteById(orderId);
    return orderId;
  }

  @Override
  public OrderDTO changeOrderStatus(Integer orderId, String status)
      throws OrderStatusNotFoundException {
    if (Arrays.stream(OrderStatus.values()).anyMatch(st -> st.name().equals(status))) {
      Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
      order.setStatus(OrderStatus.valueOf(status));
      orderRepository.save(order);
      return convert(order);
    } else {
      throw new OrderStatusNotFoundException();
    }
  }

  @Override
  public OrderDTO deleteItemFromOrder(Integer orderId, DeleteItemFromOrderByAdminDTO deleteItemFromOrderByAdminDTO) {
    Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    OrderItem item = productService.findOrderItemById(deleteItemFromOrderByAdminDTO.getItemId());
    if (order.getItems().contains(item)) {
      order.getItems().remove(item);
      orderRepository.save(order);
      productService.deleteOrderItem(item);
      return convert(order);
    } else {
      throw new OrderItemNotFoundException();
    }
  }

  @Override
  public OrderDTO getOrder(Integer orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    return convert(order);
  }

  private OrderItem convertBasketItemToOrderItem(BasketItem basketItem, Order order) {
    OrderItem orderItem = new OrderItem();
    orderItem.setProduct(basketItem.getProduct());
    orderItem.setQuantity(basketItem.getQuantity());
    orderItem.setPrice(basketItem.getPrice());
    orderItem.setOrder(order);
    return orderItem;
  }

  private void setTotalPrice(Order order) {
    Integer totalPrice = order.getItems().stream()
        .mapToInt(p -> p.getPrice() * p.getQuantity())
        .sum();
    order.setTotalPrice(totalPrice);
  }

  private OrderDTO convert(Order order) {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderId(order.getId());
    orderDTO.setPurchaseDate(order.getPurchaseDate());
    orderDTO.setItems(order.getItems());
    orderDTO.setTotalPrice(order.getTotalPrice());
    orderDTO.setStatus(order.getStatus());
    orderDTO.setShippingAddress(order.getShippingAddress());
    orderDTO.setShippingDate(order.getShippingDate());
    orderDTO.setPaid(order.isPaid());
    return orderDTO;
  }
}

package com.greenfox.masterworkwebshop.products.services;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductCategoryDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.UpdateProductDTO;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.models.entities.ProductCategory;
import com.greenfox.masterworkwebshop.products.models.enums.Category;
import java.util.List;

public interface ProductService {

  Product createProduct(CreateProductDTO createProductDTO);

  ProductCategory createProductCategory(CreateProductCategoryDTO category);

  ProductCategory findProductCategoryByName(Category category);

  List<Product> listProducts(String category);

  Integer deleteProduct(Integer productId);

  Product updateProduct(Integer id, UpdateProductDTO updateProductDTO);

  Product deleteCategoriesProduct(Integer id);

  Product deleteProductFans(Integer id);

  BasketItem saveBasketItem(BasketItem basketItem);

  Product findProductById(Integer id);

  void deleteBasketItem(BasketItem basketItem);

  void deleteAllBasketItems(Basket basket);

  List<OrderItem> saveOrderItem(List<OrderItem> orderItems);

  OrderItem findOrderItemById(Integer orderItemId);

  void deleteOrderItem(OrderItem item);

  Product getProduct(Integer id);
}

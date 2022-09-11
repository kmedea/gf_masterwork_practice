package com.greenfox.masterworkwebshop.products.services;

import com.greenfox.masterworkwebshop.baskets.models.entities.Basket;
import com.greenfox.masterworkwebshop.exceptions.types.OrderItemNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductCategoryAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductCategoryNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductNotFoundException;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductCategoryDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.UpdateProductDTO;
import com.greenfox.masterworkwebshop.products.models.entities.BasketItem;
import com.greenfox.masterworkwebshop.products.models.entities.OrderItem;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.models.entities.ProductCategory;
import com.greenfox.masterworkwebshop.products.models.enums.Category;
import com.greenfox.masterworkwebshop.products.repositories.BasketItemRepository;
import com.greenfox.masterworkwebshop.products.repositories.OrderItemRepository;
import com.greenfox.masterworkwebshop.products.repositories.ProductCategoryRepository;
import com.greenfox.masterworkwebshop.products.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl
    implements ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository categoryRepository;
  private final BasketItemRepository basketItemRepository;
  private final OrderItemRepository orderItemRepository;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      ProductCategoryRepository categoryRepository,
      BasketItemRepository basketItemRepository,
      OrderItemRepository orderItemRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.basketItemRepository = basketItemRepository;
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public ProductCategory createProductCategory(CreateProductCategoryDTO category) {
    if (categoryRepository.findProductCategoryByName(Category.valueOf(category.getNameOfCategory()))
        .isPresent()) {
      throw new ProductCategoryAlreadyExistException();
    }
    ProductCategory productCategory =
        new ProductCategory(Category.valueOf(category.getNameOfCategory()));
    return categoryRepository.save(productCategory);
  }

  @Override
  public ProductCategory findProductCategoryByName(Category category) {
    return categoryRepository.findProductCategoryByName(category)
        .orElseThrow(ProductCategoryNotFoundException::new);
  }

  @Override
  public List<Product> listProducts(String nameOfCategory) {
    if (nameOfCategory == null) {
      return productRepository.findAll();
    } else {
      ProductCategory category = findProductCategoryByName(Category.valueOf(nameOfCategory));
      return productRepository.findAllByCategories(category);
    }
  }

  @Override
  @Transactional
  public Integer deleteProduct(Integer productId) {
    productRepository.deleteProductById(productId);
    return productId;
  }

  @Override
  public Product updateProduct(Integer id, UpdateProductDTO updateProductDTO) {
    Product product = findProductById(id);
    product.setName(
        updateProductDTO.getName() == null ? product.getName() : updateProductDTO.getName());
    product.setStatus(
        updateProductDTO.getStatus() == null ? product.getStatus() : updateProductDTO.getStatus());
    product.getCategories().addAll(createProductCategoryList(updateProductDTO.getCategories()));

    productRepository.save(product);

    return product;
  }

  @Override
  public Product deleteCategoriesProduct(Integer id) {
    Product product = findProductById(id);
    product.getCategories().clear();
    productRepository.save(product);
    return product;
  }

  @Override
  public Product deleteProductFans(Integer id) {
    Product product = findProductById(id);
    product.getProductFans().clear();
    productRepository.save(product);
    return product;
  }

  @Override
  public BasketItem saveBasketItem(BasketItem basketItem) {
    return basketItemRepository.save(basketItem);
  }

  @Override
  public Product createProduct(CreateProductDTO createProductDTO) {
    Random randomInt = new Random();
    Set<ProductCategory> productCategories =
        createProductCategoryList(createProductDTO.getCategory());
    Product newProduct = new Product();
    newProduct.setName(createProductDTO.getName());
    newProduct.setCode(createProductDTO.getName() + randomInt.nextInt(1000));
    newProduct.setCategories(productCategories);
    return productRepository.save(newProduct);
  }

  public Product findProductById(Integer id) {
    return productRepository.findProductById(id).orElseThrow(ProductNotFoundException::new);
  }

  @Override
  public void deleteBasketItem(BasketItem basketItem) {
    basketItemRepository.delete(basketItem);
  }

  @Override
  @Transactional
  public void deleteAllBasketItems(Basket basket) {
    basketItemRepository.deleteAllByBasket(basket);
  }

  @Override
  public List<OrderItem> saveOrderItem(List<OrderItem> orderItems) {
    List<OrderItem> items = new ArrayList<>();
    Iterable<OrderItem> iterable = orderItemRepository.saveAll(orderItems);
    iterable.forEach(items::add);
    return items;
  }

  @Override
  public OrderItem findOrderItemById(Integer orderItemId) {
    return orderItemRepository.findById(orderItemId).orElseThrow(OrderItemNotFoundException::new);
  }

  @Override
  public void deleteOrderItem(OrderItem item) {
    orderItemRepository.delete(item);
  }

  @Override
  public Product getProduct(Integer id) {
    return productRepository.findProductById(id).orElseThrow(ProductNotFoundException::new);
  }


  private Set<ProductCategory> createProductCategoryList(List<String> categoryNames) {
    List<Category> category = categoryNames.stream()
        .map(Category::valueOf)
        .collect(Collectors.toList());
    return category.stream()
        .map(this::findProductCategoryByName)
        .collect(Collectors.toSet());
  }


}

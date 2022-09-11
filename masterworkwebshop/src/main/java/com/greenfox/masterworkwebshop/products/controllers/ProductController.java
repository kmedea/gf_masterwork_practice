package com.greenfox.masterworkwebshop.products.controllers;

import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductCategoryDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductDTO;
import com.greenfox.masterworkwebshop.products.models.dtos.UpdateProductDTO;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.models.entities.ProductCategory;
import com.greenfox.masterworkwebshop.products.services.ProductService;
import java.util.List;
import javax.validation.Valid;
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
public class ProductController {

  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/productcategory")
  public ResponseEntity<ProductCategory> createProductCategory(Authentication auth,
                                                               @Valid @RequestBody CreateProductCategoryDTO category) {

    return ResponseEntity.ok().body(productService.createProductCategory(category));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/product")
  public ResponseEntity<Product> createProduct(Authentication auth,
                                               @RequestBody CreateProductDTO product) {
    return ResponseEntity.ok().body(productService.createProduct(product));
  }

  @GetMapping("/product")
  public ResponseEntity<List<Product>> listProducts(@RequestParam(required = false) String category) {
    return ResponseEntity.ok().body(productService.listProducts(category));
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<Product> getProduct (@PathVariable Integer id) {
    return ResponseEntity.ok().body(productService.getProduct(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/products/{id}")
  public ResponseEntity<Integer> deleteProduct(Authentication auth, @PathVariable Integer id) {
    return ResponseEntity.ok().body(productService.deleteProduct(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/products/{id}")
  public ResponseEntity<Product> updateProduct(Authentication auth, @PathVariable Integer id,
                                         @RequestBody UpdateProductDTO updateProductDTO) {
    return ResponseEntity.ok().body(productService.updateProduct(id, updateProductDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/products/{id}/deletecategories")
  public ResponseEntity<Product> deleteCategoriesOfProduct(Authentication auth, @PathVariable Integer id) {
    return ResponseEntity.ok().body(productService.deleteCategoriesProduct(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/products/{id}/deleteproductfans")
  public ResponseEntity<Product> deleteProductFans(Authentication auth, @PathVariable Integer id) {
    return ResponseEntity.ok().body(productService.deleteProductFans(id));
  }


}

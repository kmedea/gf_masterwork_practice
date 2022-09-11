package com.greenfox.masterworkwebshop.products.controllers;

import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_ADMIN;
import static com.greenfox.masterworkwebshop.users.models.enums.UserState.ACTIVE;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfox.masterworkwebshop.TestNoSecurityConfig;
import com.greenfox.masterworkwebshop.products.models.dtos.CreateProductDTO;
import com.greenfox.masterworkwebshop.products.models.entities.Product;
import com.greenfox.masterworkwebshop.products.services.ProductServiceImpl;
import com.greenfox.masterworkwebshop.users.models.entities.User;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProductServiceImpl productService;

  User john;
  Authentication johnAuth;

  @BeforeEach
  void setUp() {

    john = new User();
    john.setId(100);
    john.setFirstName("John");
    john.setLastName("Doe");
    john.setEmail("johnd@test.hu");
    john.setPassword("password");
    john.setAddress("Budapest");
    john.setRole(ROLE_ADMIN);
    john.setState(ACTIVE);
    john.setEnabled(true);

    johnAuth = new UsernamePasswordAuthenticationToken(john, null, null);
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_createProductCategory() throws Exception {

    String jsonRequest =
        String.format("{\"nameOfCategory\" : \"%s\"}", "Z");

    mockMvc.perform(MockMvcRequestBuilders.post("/productcategory")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Z"));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenProductCategoryAlreadyExist_createProductCategory() throws Exception {

    String jsonRequest =
        String.format("{\"nameOfCategory\" : \"%s\"}", "A");

    mockMvc.perform(MockMvcRequestBuilders.post("/productcategory")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(409))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Product category is already taken."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenNameOfCategoryIsEmpty_createProductCategory() throws Exception {

    String jsonRequest =
        String.format("{\"nameOfCategory\" : \"%s\"}", "");

    mockMvc.perform(MockMvcRequestBuilders.post("/productcategory")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Name of category is required."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_createProduct() throws Exception{

    String jsonRequest = "{\"name\" : \"zsámoly\", \"category\": [\"A\",\"B\"]}";

    mockMvc.perform(MockMvcRequestBuilders.post("/product")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("zsámoly"))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
  }

  @Test
  void successfull_listProducts() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/product")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[1].id").value(100))
        .andExpect(jsonPath("$[1].code").value("asztal123"))
        .andExpect(jsonPath("$[1].name").value("asztal"));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_deleteProduct() throws Exception{
    CreateProductDTO createProductDTO = new CreateProductDTO();
    createProductDTO.setName("lámpa");
    createProductDTO.setCategory(Arrays.asList("A"));
    Product product = productService.createProduct(createProductDTO);
    product.setId(999);

    mockMvc.perform(MockMvcRequestBuilders.delete("/products/999")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(999));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_updateProduct() throws Exception {

    String jsonRequest = "{\"name\" : \"zsámoly\", \"status\": \"INACTIVE\"}";

    mockMvc.perform(MockMvcRequestBuilders.put("/products/200")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("zsámoly"))
        .andExpect(jsonPath("$.status").value("INACTIVE"));
  }

  @Test
  void successfull_getProduct() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/product/100")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(100))
        .andExpect(jsonPath("$.code").value("asztal123"))
        .andExpect(jsonPath("$.name").value("asztal"));
  }


}
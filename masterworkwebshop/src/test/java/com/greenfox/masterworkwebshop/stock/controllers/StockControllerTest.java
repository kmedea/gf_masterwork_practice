package com.greenfox.masterworkwebshop.stock.controllers;

import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_ADMIN;
import static com.greenfox.masterworkwebshop.users.models.enums.UserState.ACTIVE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfox.masterworkwebshop.TestNoSecurityConfig;
import com.greenfox.masterworkwebshop.users.models.entities.User;
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
class StockControllerTest {

  @Autowired
  MockMvc mockMvc;

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
  void successfull_addItemToStock() throws Exception{

    String jsonRequest =
        String.format("{\"productId\" : %d, \"quantity\" : %d}", 400, 3);

    mockMvc.perform(MockMvcRequestBuilders.post("/stock")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product.id").value(400))
        .andExpect(jsonPath("$.quantity").value(3));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenQuantityIsEmpty_addItemToStock() throws Exception{

    String jsonRequest =
        String.format("{\"productId\" : %d }", 400);

    mockMvc.perform(MockMvcRequestBuilders.post("/stock")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Quantity must not be null."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenQuantityIsNegative_addItemToStock() throws Exception{

    String jsonRequest =
        String.format("{\"productId\" : %d, \"quantity\" : %d}", 400, -2);

    mockMvc.perform(MockMvcRequestBuilders.post("/stock")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(403))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Amount must be positive number."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_deleteItemFromStock() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.delete("/stock/50")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(50))
        .andExpect(jsonPath("$.product.id").value(500));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenProductNotFound_deleteItemFromStock() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.delete("/stock/25")
            .principal(johnAuth))
        .andExpect(status().is(403))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Stock item not found."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfullRaiseAmount_changeItemAmount() throws Exception {

    String jsonRequest =
        String.format("{\"amount\" : %d}", 10);

    mockMvc.perform(MockMvcRequestBuilders.put("/stock/600")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product.id").value(600))
        .andExpect(jsonPath("$.quantity").value(110));
  }
  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfullReduceAmount_changeItemAmount() throws Exception {

    String jsonRequest =
        String.format("{\"amount\" : %d}", -2);

    mockMvc.perform(MockMvcRequestBuilders.put("/stock/500")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product.id").value(500))
        .andExpect(jsonPath("$.quantity").value(98));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenQuantityIsEmpty_changeItemAmount() throws Exception{

    String jsonRequest =
        String.format("{ }");

    mockMvc.perform(MockMvcRequestBuilders.put("/stock/500")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Amount must not be empty."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void whenStockItemIsNotEnough_changeItemAmount() throws Exception{

    String jsonRequest =
        String.format("{\"amount\" : %d}", -120);

    mockMvc.perform(MockMvcRequestBuilders.put("/stock/500")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(403))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Stock item is not enough."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_getStock() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/stock")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[5].id").value(70))
        .andExpect(jsonPath("$[5].product.id").value(700));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_getStockItem() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/stock/70")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.product.id").value(700))
        .andExpect(jsonPath("$.quantity").value(100));
  }
}
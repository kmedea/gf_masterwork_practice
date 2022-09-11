package com.greenfox.masterworkwebshop.baskets.controllers;

import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_ADMIN;
import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_USER;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
class BasketControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  BasketController basketController;

  User john;
  User jack;
  User jane;
  User manc;

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

    jack = new User();
    jack.setId(200);
    jack.setFirstName("Jack");
    jack.setLastName("Doe");
    jack.setEmail("jackd@test.hu");
    jack.setPassword("password");
    jack.setAddress("Pécs");
    jack.setRole(ROLE_USER);
    jack.setState(ACTIVE);
    jack.setEnabled(true);

    jane = new User();
    jane.setId(999);
    jane.setFirstName("Jane");
    jane.setLastName("Doe");
    jane.setEmail("janed@test.hu");
    jane.setPassword("password");
    jane.setAddress("Debrecen");
    jane.setRole(ROLE_USER);
    jane.setState(ACTIVE);
    jane.setEnabled(true);

    manc = new User();
    manc.setId(300);
    manc.setFirstName("Manci");
    manc.setLastName("Doe");
    manc.setEmail("mancid@test.hu");
    manc.setPassword("password");
    manc.setAddress("Miskolc");
    manc.setRole(ROLE_USER);
    manc.setState(ACTIVE);
    manc.setEnabled(true);
  }

  @Test
  void successfull_addToBasketWithQuality() throws Exception {
    Authentication johnAuth = new UsernamePasswordAuthenticationToken(john, null, null);

    String jsonRequest =
        String.format("{\"productId\" : %d, \"price\" : %d, \"quantity\" : %d}", 300, 5000, 1);

    mockMvc.perform(MockMvcRequestBuilders.post("/basket")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(100))
        .andExpect(jsonPath("$.totalPrice").value(9000))
        .andExpect(jsonPath("$.basketStatus").value("ACTIVE"));
  }

  @Test
  void missingQuantityWhenAddProductToBasket() throws Exception {
    Authentication johnAuth = new UsernamePasswordAuthenticationToken(john, null, null);
    String jsonRequest =
        String.format("{\"productId\" : %d, \"price\" : %d}", 300, 5000);

    mockMvc.perform(MockMvcRequestBuilders.post("/basket")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Quantity may not be empty"));
  }


  @Test
  void successfull_changeAmountInBasket() throws Exception {
    Authentication jackAuth = new UsernamePasswordAuthenticationToken(jack, null, null);

    String jsonRequest =
        String.format("{\"itemId\" : %d, \"amount\" : %d}", 500, 4);

    mockMvc.perform(MockMvcRequestBuilders.put("/basket")
            .principal(jackAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(200))
        .andExpect(jsonPath("$.items[1].quantity").value(4))
        .andExpect(jsonPath("$.totalPrice").value(7000));
  }

  @Test
  void nullAmount_changeAmountInBasket() throws Exception {
    Authentication jackAuth = new UsernamePasswordAuthenticationToken(jack, null, null);
    String jsonRequest =
        String.format("{\"itemId\" : %d}", 500);

    mockMvc.perform(MockMvcRequestBuilders.put("/basket")
            .principal(jackAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Amount must not be null or empty."));
  }

  @Test
  void negativeAmount_changeAmountInBasket() throws Exception {
    Authentication jackAuth = new UsernamePasswordAuthenticationToken(jack, null, null);
    String jsonRequest =
        String.format("{\"itemId\" : %d, \"amount\" : %d}", 500, -2);

    mockMvc.perform(MockMvcRequestBuilders.put("/basket")
            .principal(jackAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(403))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Amount must be positive number."));
  }

  @Test
  void successfull_getBasket() throws Exception {
    Authentication johnAuth = new UsernamePasswordAuthenticationToken(john, null, null);
    mockMvc.perform(MockMvcRequestBuilders.get("/basket")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(100))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.totalPrice").value(4000))
        .andExpect(jsonPath("$.basketStatus").value("ACTIVE"));
  }


  @Test
  void successfull_deleteItemFromBasket() throws Exception {
    Authentication janeAuth = new UsernamePasswordAuthenticationToken(jane, null, null);

    String jsonRequest =
        String.format("{\"itemId\" : %d}", 900);

    mockMvc.perform(MockMvcRequestBuilders.delete("/basket")
            .principal(janeAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(999))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.totalPrice").value(3000))
        .andExpect(jsonPath("$.basketStatus").value("ACTIVE"));
  }

  @Test
  void wrongProductId_deleteItemFromBasket() throws Exception {
    Authentication janeAuth = new UsernamePasswordAuthenticationToken(jane, null, null);
    String jsonRequest =
        String.format("{\"itemId\" : %d}", 5);

    mockMvc.perform(MockMvcRequestBuilders.delete("/basket")
            .principal(janeAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Product not found."));
  }

  @Test
  void flushBasket() throws Exception {
    Authentication mancAuth = new UsernamePasswordAuthenticationToken(manc, null, null);
    mockMvc.perform(MockMvcRequestBuilders.delete("/basket/flush")
            .principal(mancAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(300))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(0)))
        .andExpect(jsonPath("$.totalPrice").value(0))
        .andExpect(jsonPath("$.basketStatus").value("ACTIVE"));
  }


}
package com.greenfox.masterworkwebshop.orders.controllers;

import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_ADMIN;
import static com.greenfox.masterworkwebshop.users.models.enums.UserRole.ROLE_USER;
import static com.greenfox.masterworkwebshop.users.models.enums.UserState.ACTIVE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.greenfox.masterworkwebshop.TestNoSecurityConfig;
import com.greenfox.masterworkwebshop.baskets.controllers.BasketController;
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
class OrderControllerTest {

  @Autowired
  MockMvc mockMvc;

  User john;
  User jack;
  User jane;
  Authentication johnAuth;
  Authentication jackAuth;
  Authentication janeAuth;

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
    jane.setRole(ROLE_ADMIN);
    jane.setState(ACTIVE);
    jane.setEnabled(true);

    johnAuth = new UsernamePasswordAuthenticationToken(john, null, null);
    jackAuth = new UsernamePasswordAuthenticationToken(jack, null, null);
    janeAuth = new UsernamePasswordAuthenticationToken(jane, null, jane.getAuthorities());
  }

  @Test
  void successfull_createOrder() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/order")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.totalPrice").value(4000))
        .andExpect(jsonPath("$.shippingAddress").value("Budapest"))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
  }

  @Test
  void successfull_listAllOrderByUser() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/order")
            .principal(jackAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].items").isArray())
        .andExpect(jsonPath("$[0].items", hasSize(4)))
        .andExpect(jsonPath("$[0].totalPrice").value(7000))
        .andExpect(jsonPath("$[0].shippingAddress").value("Pécs"))
        .andExpect(jsonPath("$[0].status").value("ACTIVE"));
  }

  @Test
  void successfull_listAllOrderByUser_whenAddStatus() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/order?orderStatus=DELIVERED")
            .principal(jackAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].items").isArray())
        .andExpect(jsonPath("$[0].items", hasSize(1)))
        .andExpect(jsonPath("$[0].totalPrice").value(6000))
        .andExpect(jsonPath("$[0].shippingAddress").value("Pécs"))
        .andExpect(jsonPath("$[0].status").value("DELIVERED"));
  }

  @Test
  void successfull_getOrder() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/order/100")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderId").value(100))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.totalPrice").value(4000))
        .andExpect(jsonPath("$.shippingAddress").value("Budapest"))
        .andExpect(jsonPath("$.paid").value(true))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
  }

  @Test
  void whenOrderNotFound_getOrder() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/order/8")
            .principal(johnAuth))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.status").value("error"))
        .andExpect(jsonPath("$.message").value("Order not found."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_listAllOrderByAdmin() throws Exception {
    String jsonRequest =
        String.format("{\"userId\" : %d}", 200);

    mockMvc.perform(MockMvcRequestBuilders.get("/orders")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[1].items").isArray())
        .andExpect(jsonPath("$[1].items", hasSize(1)))
        .andExpect(jsonPath("$[1].totalPrice").value(6000))
        .andExpect(jsonPath("$[1].shippingAddress").value("Pécs"))
        .andExpect(jsonPath("$[1].status").value("DELIVERED"));
  }


  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void finalDeleteOrderByAdmin() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/orders/700")
            .principal(johnAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value("A 700 id-val rendelkező rendelés törölve lett."));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void deleteItemFromOrder() throws Exception {

    String jsonRequest =
        String.format("{\"itemId\" : %d}", 1100);

    mockMvc.perform(MockMvcRequestBuilders.delete("/order/800")
            .principal(johnAuth)
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderId").value(800))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.totalPrice").value(6000))
        .andExpect(jsonPath("$.shippingAddress").value("Miskolc"))
        .andExpect(jsonPath("$.paid").value(true))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
  }

  @Test
  @WithMockUser(authorities = {"ROLE_ADMIN"})
  void successfull_changeOrderStatus() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.put("/order/500?status=DELIVERED")
            .principal(janeAuth))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderId").value(500))
        .andExpect(jsonPath("$.items").isArray())
        .andExpect(jsonPath("$.items", hasSize(0)))
        .andExpect(jsonPath("$.totalPrice").value(8000))
        .andExpect(jsonPath("$.shippingAddress").value("Debrecen"))
        .andExpect(jsonPath("$.paid").value(true))
        .andExpect(jsonPath("$.status").value("DELIVERED"));
  }
}
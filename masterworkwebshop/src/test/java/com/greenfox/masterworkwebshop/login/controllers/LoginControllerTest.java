package com.greenfox.masterworkwebshop.login.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Pattern;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void successfulLogin() throws Exception {
    String jsonRequest = "{\"userName\" : \"JaneD\",  \"password\" : \"password\"}";

    String regex = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$";
    Pattern pt = Pattern.compile(regex);

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("ok")))
        .andExpect(jsonPath("$.token", new MatchesPattern(pt)));
  }

  @Test
  public void loginWithoutUserName() throws Exception {
    String jsonRequest = "{\"password\" : \"password\"}";

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void loginWithoutPassword() throws Exception {
    String jsonRequest = "{\"userName\" : \"JaneD\"}";

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  public void loginWithoutNameAndPassword() throws Exception {
    String jsonRequest = "{}";

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username and password are required.")));
  }

  @Test
  public void loginWrongUserName() throws Exception {
    String jsonRequest = "{\"userName\" : \"Malac\",  \"password\" : \"password\"}";

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(401))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }

  @Test
  public void loginWrongPassword() throws Exception {
    String jsonRequest = "{\"userName\" : \"JaneD\",  \"password\" : \"körtefa\"}";

    mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType("application/json")
            .content(jsonRequest))
        .andExpect(status().is(401))
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }
}
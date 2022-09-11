package com.greenfox.masterworkwebshop.security.authorizationexceptions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedHandler implements
    org.springframework.security.web.access.AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException)
      throws IOException, ServletException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getOutputStream()
        .println("{ \"error\": \"" + "Authentication token is invalid!" + "\" }");

  }
}

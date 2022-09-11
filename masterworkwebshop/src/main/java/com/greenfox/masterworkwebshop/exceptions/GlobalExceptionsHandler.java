package com.greenfox.masterworkwebshop.exceptions;

import com.greenfox.masterworkwebshop.exceptions.responses.ErrorMessage;
import com.greenfox.masterworkwebshop.exceptions.types.BasketNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.BlockedUserException;
import com.greenfox.masterworkwebshop.exceptions.types.NegativeProductAmountException;
import com.greenfox.masterworkwebshop.exceptions.types.OrderItemNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.OrderNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.OrderStatusNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductCategoryAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductCategoryNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductInactiveException;
import com.greenfox.masterworkwebshop.exceptions.types.ProductNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.StockException;
import com.greenfox.masterworkwebshop.exceptions.types.StockItemNotFoundException;
import com.greenfox.masterworkwebshop.exceptions.types.StockItemAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.UserAlreadyExistException;
import com.greenfox.masterworkwebshop.exceptions.types.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    ErrorMessage response = new ErrorMessage();
    int numberOfErrors = ex.getBindingResult().getAllErrors().size();
    String errorCode = ex.getBindingResult().getFieldError().getCode();
    if (numberOfErrors == 2) {
      response.setMessage("Username and password are required.");
      return ResponseEntity.status(400).body(response);
    } else if (numberOfErrors == 1 && errorCode.equals("Size")) {
      response.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
      return ResponseEntity.status(406).body(response);
    } else if (numberOfErrors == 1 && errorCode.equals("NotNull") || errorCode.equals("NotBlank")) {
      response.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
      return ResponseEntity.status(400).body(response);
    }
    return ResponseEntity.status(400).body(response);
  }

  @ExceptionHandler({NegativeProductAmountException.class, StockItemNotFoundException.class,
      StockException.class})
  public ResponseEntity<ErrorMessage> handleNegativeProductAmountException(RuntimeException ex) {
    ErrorMessage response = new ErrorMessage();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(403).body(response);
  }

  @ExceptionHandler({UserNotFoundException.class, ProductCategoryNotFoundException.class,
      ProductNotFoundException.class, OrderNotFoundException.class,
      OrderStatusNotFoundException.class, OrderItemNotFoundException.class,
      BasketNotFoundException.class})
  public ResponseEntity<ErrorMessage> handleUserNotFoundException(RuntimeException ex) {
    ErrorMessage response = new ErrorMessage();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(404).body(response);
  }

  @ExceptionHandler({UserAlreadyExistException.class, ProductCategoryAlreadyExistException.class,
      ProductInactiveException.class, BlockedUserException.class, StockItemAlreadyExistException.class})
  public ResponseEntity<ErrorMessage> handleUserAlreadyExistException(RuntimeException ex) {
    ErrorMessage response = new ErrorMessage();
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(409).body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorMessage> handleInvocationTargetException() {
    ErrorMessage response = new ErrorMessage();
    response.setMessage("Username or password is incorrect.");
    return ResponseEntity.status(401).body(response);
  }
}

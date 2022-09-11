package com.greenfox.masterworkwebshop.exceptions.responses;

public class ErrorMessage {

  private String status;
  private String message;

  public ErrorMessage() {
    this.status = "error";
  }

  public ErrorMessage(String message) {
    this();
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

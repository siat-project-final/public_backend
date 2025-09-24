package com.takoyakki.backend.common.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends RuntimeException {
  private final HttpStatus status;
  private final String message;

  public TokenExpiredException(String message) {
    super(message);
    this.status = HttpStatus.UNAUTHORIZED;
    this.message = message;
  }
}

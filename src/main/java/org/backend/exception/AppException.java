package org.backend.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
  private final String errorCode;
  private final HttpStatus status;
  private final Map<String, Object> params;

  public AppException(String errorCode, HttpStatus status) {
    this(errorCode, status, Map.of());
  }

  public AppException(String errorCode, HttpStatus status, Map<String, Object> params) {
    super(errorCode);
    this.errorCode = errorCode;
    this.status = status;
    this.params = params;
  }
}

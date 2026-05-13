package org.backend.exception;

import org.backend.model.dto.ApiError;
import org.backend.model.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiErrorResponse> handleAppException(AppException ex) {
    var error = new ApiError(ex.getErrorCode(), null, ex.getParams());
    var body = new ApiErrorResponse(ex.getStatus().value(), List.of(error), Instant.now());
    return ResponseEntity.status(ex.getStatus().value()).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> new ApiError(
            "VALIDATION_" + Objects.requireNonNullElse(fe.getCode(), "INVALID").toUpperCase(),
            fe.getField(),
            Map.of()))
        .toList();

    return ResponseEntity.badRequest().body(
        new ApiErrorResponse(400, errors, Instant.now()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    var error = new ApiError("INVALID_CREDENTIALS", null, Map.of());
    var body = new ApiErrorResponse(401, List.of(error), Instant.now());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }
}

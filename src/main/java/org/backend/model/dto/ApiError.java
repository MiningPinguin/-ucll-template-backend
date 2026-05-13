package org.backend.model.dto;

import java.util.Map;

public record ApiError(
    String code,
    String field,
    Map<String, Object> params) {
  // Compact constructor for when field/params aren't needed
  public ApiError(String code) {
    this(code, null, Map.of());
  }
}

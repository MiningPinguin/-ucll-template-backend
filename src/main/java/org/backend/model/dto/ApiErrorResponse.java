package org.backend.model.dto;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
    int status,
    List<ApiError> errors,
    Instant timestamp) {
}
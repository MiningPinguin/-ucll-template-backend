package org.backend.model.dto;

import org.backend.model.Role;

public record AuthenticationResponse(
    String message,
    String token,
    String username,
    Role role) {
}

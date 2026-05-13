package org.backend.model.dto;

public record AuthenticationRequest(
    String username,
    String password) {
}

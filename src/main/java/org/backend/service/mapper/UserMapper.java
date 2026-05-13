package org.backend.service.mapper;

import org.backend.model.User;
import org.backend.model.dto.UserResponse;

public class UserMapper {
  public static UserResponse toResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getUsername());
  }
}

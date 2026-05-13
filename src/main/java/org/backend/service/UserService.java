package org.backend.service;

import org.backend.exception.AppException;
import org.backend.model.Role;
import org.backend.model.User;
import org.backend.model.dto.AuthenticationResponse;
import org.backend.model.dto.UserRequest;
import org.backend.model.dto.UserResponse;
import org.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.backend.service.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Authenticates with a given username and password
   *
   * @param username the user's username
   * @param password the user's password (in plaintext)
   * @return an AuthenticationResponse containing a JWT
   */
  public AuthenticationResponse authenticate(String username, String password) {
    final var usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(username, password);
    final var authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
    final var user = ((UserDetailsImpl) authentication.getPrincipal()).user();
    final var token = jwtService.generateToken(user);
    return new AuthenticationResponse(
        "Authentication successful.",
        token,
        user.getUsername(),
        user.getRole());
  }

  public User signup(Role role, UserRequest userInput) {
    if (userRepository.existsByUsername(userInput.username())) {
      throw new AppException("USERNAME_TAKEN", HttpStatus.CONFLICT);
    }

    final var hashedPassword = passwordEncoder.encode(userInput.password());
    User user = User.builder()
        .firstName(userInput.firstName())
        .lastName(userInput.lastName())
        .username(userInput.username())
        .email(userInput.email())
        .password(hashedPassword)
        .role(role)
        .build();

    return Objects.requireNonNull(userRepository.save(user));
  }

  public UserResponse getUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return UserMapper.toResponse(user);
  }

  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return UserMapper.toResponse(user);
  }
}

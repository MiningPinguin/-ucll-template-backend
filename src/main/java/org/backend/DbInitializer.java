package org.backend;

import org.backend.model.Role;
import org.backend.model.User;
import org.backend.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DbInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DbInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.count() == 0) {
      userRepository.save(User.builder()
          .firstName("admin")
          .lastName("admin")
          .username("admin")
          .email("admin@example.com")
          .password(passwordEncoder.encode("admin"))
          .role(Role.USER)
          .build());
    }
  }
}
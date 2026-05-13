package org.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.backend.model.Role;
import org.backend.model.User;
import org.backend.model.dto.AuthenticationRequest;
import org.backend.model.dto.AuthenticationResponse;
import org.backend.model.dto.UserRequest;
import org.backend.model.dto.UserResponse;
import org.backend.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{username}")
  public UserResponse getUser(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  @PostMapping("/signup")
  public User signup(@Valid @RequestBody UserRequest userInput) {
    return userService.signup(Role.USER, userInput);
  }

  @PostMapping("/login")
  public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse httpServletResponse) {
    var auth = userService.authenticate(authenticationRequest.username(), authenticationRequest.password());

    ResponseCookie responseCookie = ResponseCookie.from("authToken", auth.token())
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(3600)
        .sameSite(SameSiteCookies.NONE.toString())
        .build();
    httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

    auth = new AuthenticationResponse(auth.message(), null, auth.username(), auth.role());
    return ResponseEntity.ok(auth);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse httpServletResponse) {

    ResponseCookie deleteCookie = ResponseCookie.from("authToken", "")
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(0)
        .sameSite("None")
        .build();

    httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

    return ResponseEntity.ok().build();
  }
}

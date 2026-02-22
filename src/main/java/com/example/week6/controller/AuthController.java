package com.example.week6.controller;

import com.example.week6.entity.User;
import com.example.week6.service.AuthService;
import com.example.week6.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final String SESSION_USER_ID = "USER_ID";
  private static final String SESSION_USER_EMAIL = "USER_EMAIL";
  private static final String SESSION_USER_ROLE = "USER_ROLE";

  private final UserService userService;
  private final AuthService authService;

  public AuthController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    User user = userService.register(request.email(), request.password());
    return ResponseEntity.status(201).body(
      Map.of("message", "User registered", "id", user.getId(), "email", user.getEmail())
    );
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
    User user = authService.authenticate(request.email(), request.password());

    session.setAttribute(SESSION_USER_ID, user.getId());
    session.setAttribute(SESSION_USER_EMAIL, user.getEmail());
    session.setAttribute(SESSION_USER_ROLE, user.getRole());

    return ResponseEntity.ok(Map.of(
      "message", "Login successful",
      "email", user.getEmail(),
      "role", user.getRole()
    ));
  }
  
  @GetMapping("/me")
  public ResponseEntity<?> me(HttpSession session) {
      Long userId = (Long) session.getAttribute(SESSION_USER_ID);
      if (userId == null) {
          return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));
      }
      return ResponseEntity.ok(Map.of(
          "id", userId,
          "email", session.getAttribute(SESSION_USER_EMAIL),
          "role", session.getAttribute(SESSION_USER_ROLE)
      ));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpSession session) {
      session.invalidate(); // Destroys the session data
      return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
  }
}

record RegisterRequest(String email, String password) {}
record LoginRequest(String email, String password) {}
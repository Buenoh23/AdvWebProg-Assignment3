package com.example.week6.service;

import com.example.week6.entity.User;
import com.example.week6.repository.UserRepositoryEm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepositoryEm userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepositoryEm userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User authenticate(String email, String plainPassword) {
    if (email == null || email.isBlank() || plainPassword == null) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    String normalizedEmail = email.trim().toLowerCase();

    User user = userRepository.findByEmail(normalizedEmail)
      .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!passwordEncoder.matches(plainPassword, user.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    return user;
  }
}
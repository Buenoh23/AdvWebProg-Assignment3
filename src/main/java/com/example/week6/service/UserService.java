package com.example.week6.service;

import com.example.week6.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final EntityManager entityManager;

  public UserService(PasswordEncoder passwordEncoder, EntityManager entityManager) {
    this.passwordEncoder = passwordEncoder;
    this.entityManager = entityManager;
  }

  @Transactional
  public User register(String email, String plainPassword) {
    if (email == null || email.isBlank() || plainPassword == null || plainPassword.isBlank()) {
      throw new IllegalArgumentException("Email/password required");
    }

    String normalizedEmail = email.trim().toLowerCase();

    User user = new User();
    user.setEmail(normalizedEmail);

    String hashedPassword = passwordEncoder.encode(plainPassword);
    user.setPassword(hashedPassword);

    user.setRole("USER");

    entityManager.persist(user);
    return user;
  }
}
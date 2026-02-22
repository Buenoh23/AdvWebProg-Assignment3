package com.example.week6.repository;

import com.example.week6.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryEm {

  private final EntityManager entityManager;

  public UserRepositoryEm(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Optional<User> findByEmail(String email) {
    String jpql = "SELECT u FROM User u WHERE u.email = :email";
    try {
      User user = entityManager.createQuery(jpql, User.class)
        .setParameter("email", email)
        .getSingleResult();
      return Optional.of(user);
    } catch (NoResultException ex) {
      return Optional.empty();
    }
  }
}
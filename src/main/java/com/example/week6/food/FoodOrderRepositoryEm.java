package com.example.week6.food;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class FoodOrderRepositoryEm {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(FoodOrder order) {
        entityManager.persist(order);
    }
}
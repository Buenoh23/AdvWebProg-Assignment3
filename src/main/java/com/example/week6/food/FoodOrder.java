package com.example.week6.food;

import jakarta.persistence.*;

@Entity
@Table(name = "food_orders")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String menuItem;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = true, length = 80)
    private String notes;

    public FoodOrder() { }

    public FoodOrder(Long userId, String menuItem, int quantity, double unitPrice, double totalPrice, String notes) {
        this.userId = userId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public String getNotes() { return notes; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setMenuItem(String menuItem) { this.menuItem = menuItem; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setNotes(String notes) { this.notes = notes; }
}
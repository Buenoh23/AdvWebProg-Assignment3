package com.example.week6.food;

public class FoodOrderRequest {

    private String menuItem;
    private int quantity;
    private String notes;

    public FoodOrderRequest() { }

    public String getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public String getNotes() { return notes; }

    public void setMenuItem(String menuItem) { this.menuItem = menuItem; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setNotes(String notes) { this.notes = notes; }
}
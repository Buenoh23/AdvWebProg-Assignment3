package com.example.week6.food;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FoodService {

    private final FoodOrderRepositoryEm orderRepository;
    private static final double TAX_RATE = 0.12;

    // Hardcoded menu map from the assignment instructions
    private final Map<String, Double> menu = Map.of(
            "Chicken Shawarma Wrap", 11.50,
            "Veggie Bowl", 10.00,
            "Iced Latte", 4.75,
            "Chocolate Cookie", 2.25
    );

    public FoodService(FoodOrderRepositoryEm orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional // Required because we are saving to the database
    public FoodOrder placeOrderForUser(Long userId, FoodOrderRequest request) {

        // TODO 1: Validate request is not null
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        // TODO 2: Validate menuItem (required, trim, not blank)
        String menuItem = request.getMenuItem();
        if (menuItem == null || menuItem.trim().isBlank() || !menu.containsKey(menuItem.trim())) {
            // Throws error if item is missing or not in our hardcoded menu map
            throw new IllegalArgumentException("Unknown menu item: " + menuItem);
        }
        menuItem = menuItem.trim();

        // TODO 3: Validate quantity (must be > 0 and <= 10)
        int quantity = request.getQuantity();
        if (quantity <= 0 || quantity > 10) {
            // Matches the expected 400 error message from the PDF's Postman tests
            throw new IllegalArgumentException("Invalid quantity");
        }

        // TODO 4: Validate notes (optional). If provided, max length 80.
        String notes = request.getNotes();
        if (notes != null && notes.length() > 80) {
            throw new IllegalArgumentException("Notes cannot exceed 80 characters");
        }

        // TODO 5: Look up unitPrice from menu and compute totals
        double unitPrice = menu.get(menuItem);
        double subtotal = unitPrice * quantity;
        double totalPrice = subtotal * (1 + TAX_RATE); // Calculates total with 12% tax

        // TODO 6: Create FoodOrder entity and persist
        // We instantiate the entity with the validated and calculated data
        FoodOrder order = new FoodOrder(userId, menuItem, quantity, unitPrice, totalPrice, notes);
        
        // Save it to the database using the repository
        orderRepository.save(order);

        // Return the created FoodOrder so the controller can send it back to the user
        return order;
    }
}
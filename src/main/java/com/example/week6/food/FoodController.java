package com.example.week6.food;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private static final String SESSION_USER_ID = "USER_ID";
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> placeOrder(
            @RequestBody FoodOrderRequest request,
            HttpServletRequest httpRequest
    ) {
        // TODO 7: Get the existing session only (do not create one for anonymous users)
        // Passing 'false' means: if a session doesn't exist, return null instead of making a new one.
        HttpSession session = httpRequest.getSession(false);

        // TODO 8: If session is null OR USER_ID is missing, return 401
        if (session == null || session.getAttribute(SESSION_USER_ID) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not logged in"));
        }

        // Safely extract the userId now that we know the session is valid
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);

        try {
            // TODO 9: Call foodService.placeOrderForUser(userId, request)
            FoodOrder savedOrder = foodService.placeOrderForUser(userId, request);

            // TODO 10: Return 200 OK JSON map with the confirmation details
            return ResponseEntity.ok(Map.of(
                    "message", "Order placed",
                    "menuItem", savedOrder.getMenuItem(),
                    "quantity", savedOrder.getQuantity(),
                    "unitPrice", savedOrder.getUnitPrice(),
                    "totalPrice", savedOrder.getTotalPrice()
            ));

        } catch (IllegalArgumentException ex) {
            // This catches the validation errors we threw in the FoodService (e.g., "Invalid quantity")
            // and returns them as a 400 Bad Request.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
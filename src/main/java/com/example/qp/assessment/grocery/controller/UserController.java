package com.example.qp.assessment.grocery.controller;


import com.example.qp.assessment.grocery.dto.OrderRequest;
import com.example.qp.assessment.grocery.dto.OrderResponse;
import com.example.qp.assessment.grocery.entity.*;
import com.example.qp.assessment.grocery.service.GroceryService;
import com.example.qp.assessment.grocery.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final GroceryService groceryService;
    private final OrderService orderService;

    public UserController(GroceryService groceryService, OrderService orderService) {
        this.groceryService = groceryService;
        this.orderService = orderService;
    }

    @GetMapping("/groceries")
    public List<GroceryItem> getAvailableGroceries() {

        return groceryService.getAllGroceryItems();
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        logger.info("Initiated order placement for : {}" , request );
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok().body(response);
    }
}

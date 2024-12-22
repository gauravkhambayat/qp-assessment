package com.example.qp.assessment.grocery.controller;


import com.example.qp.assessment.grocery.dto.InventoryUpdateRequest;
import com.example.qp.assessment.grocery.entity.GroceryItem;
import com.example.qp.assessment.grocery.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/admin/groceries")
public class AdminController {
    private final GroceryService groceryService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    public AdminController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @PostMapping
    public GroceryItem addGrocery(@RequestBody GroceryItem item) {
        logger.info("initiated adding grocery for item : {}",item);
        return groceryService.addGroceryItem(item);
    }

    @GetMapping
    public List<GroceryItem> getAllGroceries() {
        return groceryService.getAllGroceryItems();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> removeGrocery(@PathVariable UUID itemId) {
        groceryService.removeGroceryItem(itemId);
        return ResponseEntity.ok("Grocery item has been deleted!!");
    }

    @PutMapping("/{itemId}")
    public GroceryItem updateGrocery(@PathVariable UUID itemId, @RequestBody GroceryItem item) {
        return groceryService.updateGroceryItem(itemId, item);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> updateInventory(
            @PathVariable UUID itemId,
            @RequestBody InventoryUpdateRequest request) {
        groceryService.updateInventory(itemId, request.getInventory());
        return ResponseEntity.ok(Map.of(
                "message", "Inventory updated successfully",
                "itemId", itemId
        ));
    }
}
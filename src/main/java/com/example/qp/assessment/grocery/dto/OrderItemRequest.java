package com.example.qp.assessment.grocery.dto;

import java.util.UUID;


public class OrderItemRequest {
    private UUID itemId;
    private int quantity;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


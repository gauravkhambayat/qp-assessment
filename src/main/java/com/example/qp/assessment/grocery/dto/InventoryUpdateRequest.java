package com.example.qp.assessment.grocery.dto;

import jakarta.validation.constraints.Min;

public class InventoryUpdateRequest {
    @Min(value = 0, message = "Inventory must be a non-negative number")
    private Integer inventory;

    // Default Constructor
    public InventoryUpdateRequest() {
    }

    // Getter and Setter
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}

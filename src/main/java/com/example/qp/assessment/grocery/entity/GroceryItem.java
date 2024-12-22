package com.example.qp.assessment.grocery.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "grocery_items")
public class GroceryItem {
    @Id
    @GeneratedValue
    private UUID itemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int inventory;

    private String description;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroceryItem(UUID itemId, String description, int inventory, double price, String name) {
        this.itemId = itemId;
        this.description = description;
        this.inventory = inventory;
        this.price = price;
        this.name = name;
    }

    public GroceryItem() {
    }

}
